# Parkmate 주문 서비스 (Order Service)

Parkmate 프로젝트의 주문 및 결제 관련 기능을 담당하는 마이크로서비스입니다.

## 1. 핵심 역할 및 아키텍처

본 서비스는 사용자 주차 예약 및 결제와 관련된 핵심 비즈니스 로직을 처리합니다. 다른 마이크로서비스(예: `parkmate-parking-service`, `parkmate-payment-service`, `parkmate-user-service`)와의 연동을 통해 주문의 생성부터 완료까지의 전 과정을 관리합니다.

### 주요 기능

-   **주차 예약 생성**: 사용자의 주차 요청을 받아 예약을 생성하고, 주차 공간 가용성을 확인합니다.
-   **주문 상태 관리**: 주문의 생성, 결제 대기, 결제 완료, 취소 등 다양한 상태를 관리하고 추적합니다.
-   **결제 연동**: `parkmate-payment-service`와 연동하여 실제 결제를 처리하고, 결제 결과를 반영합니다.
-   **이벤트 기반 통신**: Kafka를 통해 다른 서비스와 비동기적으로 통신하여 시스템의 유연성과 확장성을 확보합니다.

### 주문 처리 흐름

Parkmate 주문 서비스의 핵심 주문 처리 흐름은 다음과 같습니다.

1.  **예약 생성**: 사용자가 주차 공간을 예약합니다.
2.  **주문 생성**: 예약 정보를 기반으로 주문이 생성됩니다.
3.  **결제 완료**: 사용자가 결제를 완료합니다.
4.  **주문 상태 변경**: 결제 완료에 따라 주문 상태가 업데이트됩니다.
5.  **주문 완료 이벤트 발행**: 최종적으로 주문 완료 이벤트가 발행되어 다른 관련 서비스에 알립니다.

### 아키텍처 특징

-   **마이크로서비스 아키텍처**: 독립적인 배포 및 확장이 가능한 서비스 단위로 구성됩니다.
-   **이벤트 기반 아키텍처**: Kafka를 통해 서비스 간 느슨한 결합을 유지하며, 비동기적인 데이터 동기화 및 이벤트 처리를 수행합니다.
-   **서비스 디스커버리**: Netflix Eureka를 사용하여 서비스 인스턴스를 등록하고 다른 서비스들이 이를 발견할 수 있도록 합니다.

## 2. 기술 스택

-   **언어:** Java 17
-   **프레임워크:** Spring Boot 3.4.3, Spring Cloud 2024.0.1
-   **데이터베이스:**
    -   **MySQL :** 주문 정보 저장을 위한 주 데이터 저장소
-   **메시징 큐:** Apache Kafka
-   **서비스 디스커버리:** Netflix Eureka
-   **API 문서:** Swagger (SpringDoc OpenAPI)
-   **빌드 도구:** Gradle

## 3. 프로젝트 구조

Parkmate 주문 서비스는 기능별 응집도를 높이고 유지보수성을 향상시키기 위해 다음과 같은 패키지 구조를 따릅니다.

```
src/main/java/com/parkmate/parkmateorderservice/
├── common/             # 공통 유틸리티, 설정, 예외 처리, 응답 구조
│   ├── config/         # JPA, Swagger 등 애플리케이션 공통 설정
│   ├── entity/         # 공통 엔티티 (예: BaseEntity)
│   ├── exception/      # 공통 예외 클래스 및 예외 처리 핸들러
│   ├── generator/      # 주문 코드 생성기와 같은 공통 유틸리티
│   └── response/       # API 응답 형식 (ApiResponse, CursorPage, ResponseStatus)
├── kafka/              # Kafka 메시징 관련 구성 및 로직
│   ├── config/         # Kafka Consumer/Producer 설정
│   ├── constant/       # Kafka 토픽 및 컨슈머 그룹 상수
│   ├── consumer/       # Kafka 메시지 컨슈머 (예: PaymentConsumer)
│   ├── event/          # Kafka를 통해 주고받는 이벤트 정의 (OrderEvent, PaymentEvent)
│   └── producer/       # Kafka 메시지 프로듀서 (예: OrderProducer)
└── order/              # 주문 도메인 관련 핵심 비즈니스 로직 및 구성 요소
    ├── application/    # 애플리케이션 서비스 계층 (비즈니스 로직 처리)
    │   ├── OrderService.java
    │   ├── OrderServiceImpl.java
    │   ├── dispatcher/ # 이벤트 디스패처
    │   └── handler/    # 도메인 이벤트 핸들러 (예: PaymentAbortedEventHandler)
    ├── domain/         # 도메인 모델 및 비즈니스 규칙 (엔티티, 값 객체, Enum)
    │   ├── Order.java
    │   ├── OrderStatus.java
    │   ├── OrderType.java
    │   └── PaymentType.java
    ├── dto/            # 데이터 전송 객체 (Request/Response DTO)
    │   ├── request/    # 클라이언트 요청 DTO
    │   └── response/   # 클라이언트 응답 DTO
    ├── infrastructure/ # 데이터 영속성 계층 (Repository 인터페이스 및 구현)
    │   ├── OrderCustomRepository.java
    │   ├── OrderCustomRepositoryImpl.java
    │   └── OrderRepository.java
    ├── presentation/   # 프레젠테이션 계층 (REST API 컨트롤러)
    │   └── OrderController.java
    └── vo/             # 값 객체 (Value Object) - DTO와 유사하나 특정 뷰나 내부 표현에 사용
        ├── request/
        └── response/
```

**주요 패키지 설명:**

*   **`common`**: 애플리케이션 전반에 걸쳐 사용되는 공통 기능들을 모아둔 패키지입니다. 설정, 예외 처리, 공통 엔티티 및 API 응답 형식을 정의합니다.
*   **`kafka`**: Kafka를 이용한 비동기 메시징 처리를 위한 패키지입니다. Kafka 설정, 토픽/그룹 상수, 메시지 컨슈머 및 프로듀서, 그리고 서비스 간 통신에 사용되는 이벤트 객체들을 포함합니다.
*   **`order`**: 주문 도메인의 핵심 로직을 포함하는 패키지입니다. 도메인 주도 설계(DDD) 원칙에 따라 `application`, `domain`, `dto`, `infrastructure`, `presentation`, `vo` 등으로 세분화되어 있습니다.
    *   **`application`**: 실제 비즈니스 로직을 수행하는 서비스 인터페이스 및 구현체, 그리고 도메인 이벤트를 처리하는 핸들러들을 포함합니다.
    *   **`domain`**: 주문과 관련된 핵심 도메인 모델(엔티티, 값 객체, Enum) 및 비즈니스 규칙을 정의합니다.
    *   **`dto`**: 클라이언트와 서버 간 데이터 전송에 사용되는 객체들을 정의합니다.
    *   **`infrastructure`**: 데이터베이스 접근 및 영속성 관련 로직(Repository)을 담당합니다.
    *   **`presentation`**: 외부 요청(HTTP)을 받아 처리하고 응답을 반환하는 컨트롤러를 포함합니다.
    *   **`vo`**: 특정 목적을 위한 값 객체들을 정의합니다.

## 4. API 개요

본 서비스는 주문 및 결제 관련 기능을 위한 다양한 API를 제공합니다. 주요 API는 다음과 같습니다:

-   **주차 예약 생성**: `POST /api/v1/orders`
-   **주문 내역 조회**: `POST /api/v1/orders/`
-   **주문 상세 조회**: `GET /api/v1/orders/{orderCode}`

자세한 API 명세는 Swagger UI (`/swagger-ui.html`)를 통해 확인할 수 있습니다.

## 5. 실행 방법

### 빌드

```bash
./gradlew build
```

### 실행

```bash
java -jar build/libs/parkmate-order-service-0.0.1-SNAPSHOT.jar
```

### YAML 파일을 이용한 실행

Spring Boot 애플리케이션은 `application.yml` 또는 `application.properties` 파일을 통해 설정을 관리합니다. 다음은 `application.yml` 파일의 예시입니다.

**`src/main/resources/application.yml` 예시:**

```yaml
spring:
  application:
    name: parkmate-order-service
  datasource:
    url: ${DATABASE_URL}
    username: ${DATABASE_USERNAME:user}
    password: ${DATABASE_PASSWORD:password}
  jpa:
    hibernate:
      ddl-auto: update
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}

server:
  port: <port>
eureka:
  client:
    serviceUrl:
      defaultZone: ${EUREKA_DEFAULT_ZONE:http://localhost:8761/eureka/}
```

## 6. Docker Compose 실행

`docker-compose-order.yml` 파일을 사용하여 Docker 컨테이너로 서비스를 실행할 수 있습니다.

```bash
docker-compose -f docker-compose-order.yml up -d
```
