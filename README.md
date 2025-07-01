# Parkmate Reservation Service

## 📖 프로젝트 개요

Parkmate Reservation Service는 주차장 예약 기능을 제공하는 마이크로서비스입니다. 사용자는 이 서비스를 통해 주차 공간을 예약, 수정, 취소 및 조회할 수 있습니다.

## ✨ 주요 기능

- **주차 공간 예약:** 사용자는 원하는 시간대에 주차 공간을 예약할 수 있습니다.
- **예약 관리:** 예약 내역을 수정하거나 취소할 수 있습니다.
- **예약 조회:** 자신의 예약 목록과 상세 내역을 확인할 수 있습니다.
- **내부 API 제공:** 다른 서비스에서 예약 정보를 사용할 수 있도록 내부 API를 제공합니다.

## 🛠️ 기술 스택

- **언어:** Java 17
- **프레임워크:** Spring Boot 3.4.3
- **데이터베이스:** MySQL, JPA(Hibernate), QueryDSL
- **메시징 큐:** Kafka
- **서비스 디스커버리:** Eureka
- **API 통신:** OpenFeign
- **API 문서화:** Swagger (Springdoc)

## 🚀 실행 방법

### 1. Docker Compose 사용

프로젝트 루트 디렉토리에서 아래 명령어를 실행하여 서비스를 시작할 수 있습니다.

```bash
docker-compose -f docker-compose-reservation.yml up -d
```

### 2. 직접 실행

#### 사전 요구사항

- Java 17
- Gradle

#### 빌드

```bash
./gradlew build
```

#### 실행

```bash
java -jar build/libs/parkmate-reservation-service-0.0.1-SNAPSHOT.jar
```

## 📝 API 문서

서비스가 실행되면 아래 주소에서 API 문서를 확인할 수 있습니다.

- [http://localhost:8200/swagger-ui.html](http://localhost:8200/swagger-ui.html)

## 🏛️ 아키텍처

- **MSA (Microservice Architecture):** 예약 기능은 독립적인 마이크로서비스로 구현되어 다른 서비스와 분리되어 운영됩니다.
- **Event-Driven Architecture:** Kafka를 사용하여 예약 생성 및 상태 변경과 같은 이벤트를 비동기적으로 처리합니다.
    - `ReservationProducer`: 예약 생성 이벤트를 Kafka 토픽으로 발행합니다.
    - `UserParkingHistoryConsumer`: 주차장 입출차 이력 이벤트를 구독하여 예약 상태를 변경합니다.
- **API Gateway (예상):** 외부 요청은 API Gateway를 통해 라우팅될 것으로 예상됩니다. (현재 프로젝트에는 포함되지 않음)
- **Service Discovery:** Eureka를 사용하여 서비스를 등록하고 다른 서비스를 찾습니다.
- **Internal/External API:**
    - `ReservationController`: 외부 사용자(클라이언트)를 위한 API를 제공합니다.
    - `InternalReservationController`: 다른 마이크로서비스(예: 주차장 서비스)와의 통신을 위한 내부 API를 제공합니다.

## 📁 프로젝트 구조

```
.
├── build.gradle                # 프로젝트 의존성 및 빌드 설정
├── docker-compose-reservation.yml # Docker Compose 설정 파일
├── Dockerfile                    # Docker 이미지 생성 파일
└── src
    ├── main
    │   ├── java
    │   │   └── com/parkmate/reservationservice
    │   │       ├── ReservationserviceApplication.java # Spring Boot 애플리케이션 시작점
    │   │       ├── common                # 공통 모듈 (설정, 예외 처리 등)
    │   │       ├── kafka                 # Kafka 관련 클래스 (Producer, Consumer, Event)
    │   │       └── reservation           # 예약 도메인
    │   │           ├── application       # 서비스 로직 (ReservationService)
    │   │           ├── domain            # 도메인 모델 (Reservation, ReservationStatus)
    │   │           ├── dto               # 데이터 전송 객체
    │   │           ├── infrastructure    # 외부 시스템 연동 (Repository, Feign Client)
    │   │           └── presentation      # API 컨트롤러 (ReservationController)
    │   └── resources
    │       └── application.yml       # 애플리케이션 설정 (외부 주입)
    └── test
        └── ...
```