# Parkmate 예약 서비스

Parkmate 프로젝트의 주차 예약 기능을 담당하는 마이크로서비스입니다.

## 1. 기술 스택

- **언어:** Java 17
- **프레임워크:** Spring Boot 3.4.3, Spring Cloud 2024.0.1
- **데이터베이스:**
  - **JPA (MySQL):** 예약, 결제 등 핵심 도메인 데이터 관리
  - **QueryDSL:** 동적 쿼리 생성을 통한 복잡한 조회 기능 구현
- **메시징 큐:** Apache Kafka
- **서비스 디스커버리:** Netflix Eureka
- **선언적 HTTP 클라이언트:** OpenFeign
- **API 문서:** Swagger (SpringDoc OpenAPI)
- **빌드 도구:** Gradle

## 2. 아키텍처

본 서비스는 MSA(Microservice Architecture)를 따르며, 내부적으로는 계층형 아키텍처(Presentation, Application, Domain, Infrastructure)를 적용하여 각 모듈의 독립성과 응집도를 높였습니다.

- **패키지 구조:**
  - `common`: 공통적으로 사용되는 설정, 엔티티, 예외 처리, 응답 객체 등을 포함합니다.
  - `kafka`: Kafka 메시지 발행 및 구독 관련 설정, 상수, 컨슈머, 프로듀서 로직을 포함합니다.
  - `reservation`: 예약 도메인 관련 핵심 로직을 담당하며, 계층별로 세분화되어 있습니다.
    - `application`: 비즈니스 로직을 처리하는 서비스 인터페이스 및 구현체, 이벤트 디스패처 및 핸들러를 포함합니다.
    - `domain`: 도메인 모델(엔티티, Enum) 및 비즈니스 규칙을 정의합니다.
    - `dto`: 계층 간 데이터 전송을 위한 DTO(Data Transfer Object)를 정의합니다.
    - `event`: 도메인 이벤트를 정의하며, 각 이벤트 타입별로 세분화됩니다.
    - `generator`: 예약 코드 생성과 같은 도메인 관련 유틸리티 로직을 포함합니다.
    - `infrastructure`: 외부 시스템 연동(OpenFeign 클라이언트) 및 영속성(JPA 리포지토리) 관련 로직을 포함합니다.
    - `presentation`: REST API 엔드포인트를 정의하는 컨트롤러를 포함합니다.
    - `vo`: 값 객체(Value Object)를 정의하며, 주로 요청 및 응답에 사용되는 VO를 포함합니다.
- **Eureka:** 서비스 디스커버리 서버에 자신을 등록하여 다른 서비스가 검색할 수 있도록 합니다.
- **OpenFeign:** 다른 마이크로서비스와 선언적으로 HTTP 통신을 수행합니다.
- **Kafka:** 예약 생성, 취소 등의 도메인 이벤트를 발행하여 다른 서비스와 비동기적으로 데이터를 교환합니다.

## 3. 프로젝트 구조

```
src/
├── main/
     ├── java/
     │   └── com/
     │       └── parkmate/
     │           └── reservationservice/
     │               ├── ReservationserviceApplication.java  # Spring Boot 애플리케이션 메인 클래스
     │               ├── common/                     # 공통 모듈
     │               │   ├── config/                 # 공통 설정 (JPA, Swagger)
     │               │   ├── entity/                 # 공통 엔티티 (BaseEntity)
     │               │   ├── exception/              # 공통 예외 및 예외 핸들러
     │               │   └── response/               # 공통 응답 객체 (ApiResponse, CursorPage, ResponseStatus)
     │               ├── kafka/                      # Kafka 모듈
     │               │   ├── config/                 # Kafka 컨슈머/프로듀서 설정
     │               │   │   ├── consume/            # 컨슈머 설정 (Common, Order, UserParkingHistory)
     │               │   │   └── produce/            # 프로듀서 설정 (KafkaProduceConfig)
     │               │   ├── constant/               # Kafka 관련 상수 (KafkaConsumerGroups, KafkaTopics)
     │               │   ├── consumer/               # Kafka 메시지 컨슈머 (OrderConsumer, UserParkingHistoryConsumer)
     │               │   └── producer/               # Kafka 메시지 프로듀서 (ReservationProducer)
     │               └── reservation/                # 예약 도메인 모듈
     │                   ├── application/            # 애플리케이션 계층
     │                   │   ├── ReservationService.java     # 예약 서비스 인터페이스
     │                   │   ├── ReservationServiceImpl.java # 예약 서비스 구현체
     │                   │   ├── dispatcher/         # 이벤트 디스패처 (EventDispatcher)
     │                   │   └── handler/            # 이벤트 핸들러
     │                   │       ├── EventHandler.java       # 이벤트 핸들러 인터페이스
     │                   │       ├── order/          # 주문 관련 이벤트 핸들러 (OrderCancelEventHandler, OrderCompleteEventHandler)
     │                   │       └── userparkinghistory/ # 사용자 주차 이력 관련 이벤트 핸들러 (EntryHistoryEventHandler, ExitHistoryEventHandler)
     │                   ├── domain/                 # 도메인 계층
     │                   │   ├── Reservation.java    # 예약 엔티티
     │                   │   └── ReservationStatus.java # 예약 상태 Enum
     │                   ├── dto/                    # 데이터 전송 객체 (DTO)
     │                   │   ├── request/            # 요청 DTO (PreReserveRequestDto, ReservationCancelRequestDto 등)
     │                   │   └── response/           # 응답 DTO (PreReserveResponseDto, ReservationResponseDto 등)
     │                   ├── event/                  # 도메인 이벤트 정의
     │                   │   ├── order/              # 주문 이벤트 (OrderEvent, OrderEventType)
     │                   │   ├── reservation/        # 예약 이벤트 (ReservationEvent, ReservationEventType)
     │                   │   └── userparkinghistory/ # 사용자 주차 이력 이벤트 (HistoryType, UserParkingHistoryEvent)
     │                   ├── generator/              # 예약 코드 생성 유틸리티 (ReservationCodeGenerator)
     │                   ├── infrastructure/         # 인프라 계층
     │                   │   ├── client/             # 외부 서비스 클라이언트 (ParkingServiceClient)
     │                   │   │   ├── request/        # 클라이언트 요청 DTO
     │                   │   │   └── response/       # 클라이언트 응답 DTO
     │                   │   └── repository/         # JPA 리포지토리 (ReservationRepository, ReservationRepositoryCustom 등)
     │                   ├── presentation/           # 표현 계층
     │                   │   ├── InternalReservationController.java # 내부용 REST API 컨트롤러
     │                   │   └── ReservationController.java       # 외부용 REST API 컨트롤러
     │                   └── vo/                     # 값 객체 (Value Object)
     │                       ├── ParkingSpot.java    # 주차 공간 VO
     │                       ├── request/            # 요청 VO (PreReserveRequestVo, ReservationCancelRequestVo 등)
     │                       └── response/           # 응답 VO (PreReserveResponseVo, ReservationResponseVo 등)
     └── resources/  # 애플리케이션 리소스 (application.yml 등)

```

## 4. 주요 기능

## 3. 주요 기능

- 주차장 예약 생성, 조회, 수정, 취소
- 예약 가능 시간 조회
- 사용자의 예약 내역 조회
  -   **커서 기반 페이지네이션**: `offset` 기반 페이지네이션의 한계점(예: 대량의 데이터 조회 시 성능 저하, 데이터 중복 또는 누락 가능성)을 극복하기 위해 커서 기반 페이지네이션을 도입했습니다. 이는 마지막으로 조회된 항목의 커서(예: ID 또는 타임스탬프)를 사용하여 다음 페이지를 조회하는 방식으로, 대규모 데이터셋에서도 일관되고 효율적인 페이지네이션을 가능하게 합니다.


## 4. 실행 방법

### 빌드

```bash
./gradlew build
```

### 실행

```bash
java -jar build/libs/parkmate-reservation-service-0.0.1-SNAPSHOT.jar
```

## 5. API 문서

애플리케이션 실행 후, 아래 URL을 통해 API 문서를 확인할 수 있습니다.

- [http://localhost:8200/swagger-ui.html](http://localhost:8200/swagger-ui.html)

## 6. YML 설정

```yaml
server:
  port: 8200

spring:
  application:
    name: reservation-service
  datasource:
    url: {DATABASE_URL}
    username: <username>
    password: <password>
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

  kafka:
    bootstrap-servers: {BOOTSTRAP_SERVERS}

eureka:
  instance:
    instance-id: ${spring.cloud.client.ip-address}:${spring.application.name}:${spring.application.instance_id:${random.value}}
  client:
    fetch-registry: true
    register-with-eureka: true
    service-url:
      defaultZone: http://localhost:8761/eureka


springdoc:
  swagger-ui:
    path: /swagger-ui.html
  api-docs:
    path: /v3/api-docs
```

## 7. Docker Compose 실행

`docker-compose-reservation.yml` 파일을 사용하여 Docker 컨테이너로 서비스를 실행할 수 있습니다.

```bash
docker-compose -f docker-compose-reservation.yml up -d
```

**사전 조건:**

- Docker 및 Docker Compose가 설치되어 있어야 합니다.
- `/home/ubuntu/config/reservation/application.yml` 경로에 설정 파일이 존재해야 합니다.
- `/home/ubuntu/env/.env.reservation` 경로에 환경 변수 파일이 존재해야 합니다.
- `backend` Docker 네트워크가 생성되어 있어야 합니다.
