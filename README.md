# Parkmate Notification Service

## 1. 개요

Parkmate Notification Service는 주차장 예약 및 이용과 관련된 다양한 이벤트를 처리하고, 사용자(운전자 및 주차장 호스트)에게 실시간으로 알림을 전송하는 마이크로서비스입니다.

## 2. 주요 기능

- **실시간 알림 전송**: FCM을 통한 푸시 알림 전송
- **이벤트 기반 처리**: Kafka를 통한 비동기 이벤트 처리
- **예약 알림**: 예약 완료 시 사용자 및 호스트에게 알림
- **주차 이력** 알림: 입차/출차 알림 및 출차 시간 리마인더
- **알림 관리**: 알림 조회, 읽음 처리, 삭제 기능
- **사용자 토큰 관리**: FCM 토큰 저장 및 관리

## 3. 기술 스택

- **Language**: Java 17
- **Framework**: Spring Boot 3.4.3
- **Database**:
  - **MongoDB**: 알림 메시지 등 주요 데이터를 저장하는 주 저장소로 사용됩니다.
  - **MySQL**: JPA 설정을 통해 일부 관계형 데이터를 관리합니다.
- **Messaging Queue**: Apache Kafka
- **Push Notification**: Firebase Cloud Messaging (FCM)
- **Service Discovery**: Spring Cloud Netflix Eureka
- **API Documentation**: Swagger (OpenAPI 3.0)
- **Libraries**:
  - Spring Data MongoDB & JPA
  - Spring Kafka
  - Spring WebFlux (for WebClient)
  - Lombok
  - `com.github.f4b6a3:uuid-creator`

## 4. 프로젝트 구조
```
parkmate-notification-service/
  ├── build.gradle                # (빌드 설정 및 의존성 관리)
  ├── Dockerfile                  # (Docker 이미지 생성 설정)
  ├── docker-compose-notification.yml # (Docker Compose 설정)
  ├── README.md                   # (프로젝트 설명서)
   └── src
      ├── main
      │   ├── java/com/parkmate/notificationservice/
      │   │   ├── NotificationServiceApplication.java # (Spring Boot 애플리케이션 메인 클래스)
      │   │   │
      │   │   ├── common/             # (여러 도메인에서 공통으로 사용하는 클래스)
      │   │   │   ├── config/         # (애플리케이션 설정)
      │   │   │   ├── exception/      # (예외 처리 관련 클래스)
      │   │   │   └── response/       # (공통 응답 포맷)
      │   │   │
      │   │   ├── kafka/              # (카프카 메시지 수신 및 처리)
      │   │   │   ├── config/         # (Kafka 관련 설정)
      │   │   │   ├── constant/       # (Kafka 상수 (토픽명 등))
      │   │   │   └── consumer/       # (카프카 토픽을 구독하는 컨슈머)
      │   │   │
      │   │   ├── notification/       # (알림 도메인의 핵심 로직)
      │   │   │   ├── application/    # (비즈니스 로직 (서비스 레이어))
      │   │   │   ├── domain/         # (도메인 모델 및 이벤트 처리)
      │   │   │   ├── dto/            # (데이터 전송 객체 (요청/응답))
      │   │   │   ├── event/          # (도메인 이벤트 정의)
      │   │   │   ├── infrastructure/ # (DB 연동, 외부 API 클라이언트)
      │   │   │   └── presentation/   # (외부에 노출되는 API 컨트롤러)
      │   │   │
      │   │   ├── notificationsender/ # (실제 알림(FCM)을 발송하는 로직)
      │   │   │   └── firebase/       # (Firebase Cloud Messaging(FCM) 관련 서비스)
      │   │   │
      │   │   ├── scheduler/          # (주기적인 작업을 수행하는 스케줄러)
      │   │   │
      │   │   └── usertoken/          # (사용자 디바이스 토큰 관리)
      │   │       ├── application/    # (사용자 토큰 관련 비즈니스 로직)
      │   │       ├── domain/         # (사용자 토큰 도메인 모델)
      │   │       ├── infrastructure/ # (사용자 토큰 리포지토리)
      │   │       └── presentation/   # (사용자 토큰 API 컨트롤러)
      │   │
      │   └── resources/              # (애플리케이션 리소스 (application.yml 등))
```

## 5. 아키텍처

<image src="./images/notification-architecture.png"/>

1.  **이벤트 발행 (Producers)**: 예약 서비스, 사용자 주차 내역 서비스 등 다른 마이크로서비스에서 주요 이벤트(예: 예약 생성, 입차 완료)가 발생하면 Kafka 토픽으로 메시지를 발행합니다.
2.  **이벤트 수신 (Consumers)**: Notification Service 내의 `ReservationConsumer`, `UserParkingHistoryConsumer`가 각각 해당 토픽을 구독(subscribe)하여 이벤트를 수신합니다.
3.  **알림 처리 및 저장**: 수신된 이벤트를 `NotificationEventHandler`가 처리하여 `Notification` 객체를 생성하고, MongoDB에 저장합니다.
4.  **알림 발송**: `FCMService`를 통해 해당 사용자에게 푸시 알림을 발송합니다. 주기적으로 발송 실패한 알림을 재시도하는 로직이 포함되어 있습니다.
5.  **API 제공**: `NotificationController`와 `UserTokenController`를 통해 클라이언트(모바일 앱 등)가 알림 데이터를 관리하고 FCM 토큰을 등록할 수 있는 API를 제공합니다.

## 6. API Endpoints

API에 대한 자세한 명세는 Swagger UI (`/swagger-ui.html`)를 통해 확인할 수 있습니다.

### Notification API

- **`GET /api/v1/notifications/{receiverType}`**: 알림 목록 조회
- **`PATCH /api/v1/notifications/{notificationId}/{receiverType}`**: 알림 읽음 처리
- **`DELETE /api/v1/notifications/{notificationId}/{receiverType}`**: 알림 삭제
- **`GET /api/v1/notifications/{receiverType}/unread-count`**: 읽지 않은 알림 개수 조회

> **Note**: `{receiverType}`은 `user` 또는 `host`가 될 수 있으며, 요청 시 헤더에 `X-User-UUID` 또는 `X-Host-UUID`를 포함해야 합니다.

### User Token API

- **`POST /api/v1/usertokens`**: FCM 토큰 저장
- **`GET /api/v1/usertokens`**: 사용자 UUID로 FCM 토큰 조회

## 7. 실행 방법

### 환경 설정
1. **환경 변수 설정**
```
# application.yml 또는 환경 변수로 설정
SPRING_KAFKA_BOOTSTRAP_SERVERS=localhost:9092
SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/parkmate
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/parkmate
REDIS_HOST=localhost
REDIS_PORT=6379
FIREBASE_CREDENTIALS_PATH=/path/to/firebase-credentials.json
```
2. **Firebase 설정**
- Firebase 설정
- Firebase 프로젝트 생성
- 서비스 계정 키 다운로드
- firebase-credentials.json 파일을 프로젝트 루트에 배치

### Docker Compose 사용

아래 명령어를 사용하여 Docker Compose로 서비스를 실행할 수 있습니다.

```bash
docker-compose -f docker-compose-notification.yml up -d
```

**요구사항**:

- Docker 및 Docker Compose가 설치되어 있어야 합니다.
- `/home/ubuntu/config/notification/application.yml` 경로에 설정 파일이 존재해야 합니다.
- `/home/ubuntu/env/.env.notification` 경로에 환경변수 파일이 존재해야 합니다.

### 로컬 환경에서 실행

1.  프로젝트를 클론합니다.
    ```bash
    git clone <repository-url>
    cd parkmate-notification-service
    ```
2.  `application.yml` 파일에 DB, Kafka, Eureka 등 외부 서비스 연결 정보를 설정합니다.
3.  아래 명령어로 애플리케이션을 실행합니다.
    ```bash
    ./gradlew bootRun
    ```

## 8. 주요 Kafka 토픽

- `reservation`: 예약 관련 이벤트 (생성, 수정, 취소 등)
- `user-parking-history`: 사용자 주차 내역 관련 이벤트 (입차, 출차 등)

## 9. 개발 가이드
### 새로운 알림 타입 추가
1. **이벤트 클래스 생성**
```java
public class NewEvent extends NotificationEvent {
    // 이벤트 데이터
}
```

2. **이벤트 프로세서 구현**
```java
@Component
public class NewEventProcessor implements EventProcessor<NewEvent> {
    @Override
    public CompletableFuture<List<Notification>> create(NewEvent event) {
        // 알림 생성 로직
    }
}
```

3. **Kafka 컨슈머 추가**
```java
@KafkaListener(topics = "new-event-topic")
public void consumeNewEvent(List<NewEvent> events) {
    eventHandler.handleEvent(events);
}
```