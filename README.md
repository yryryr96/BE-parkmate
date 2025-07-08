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

Parkmate 주문 서비스의 핵심 주문 처리 흐름은 다음과 같습니다:

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
    -   **PostgreSQL (또는 MongoDB):** 주문 정보 저장을 위한 주 데이터 저장소 (프로젝트 설정에 따라 변경될 수 있습니다.)
-   **메시징 큐:** Apache Kafka
-   **서비스 디스커버리:** Netflix Eureka
-   **API 문서:** Swagger (SpringDoc OpenAPI)
-   **빌드 도구:** Gradle

## 3. API 개요

본 서비스는 주문 및 결제 관련 기능을 위한 다양한 API를 제공합니다. 주요 API는 다음과 같습니다:

-   **주차 예약 생성**: `POST /api/v1/orders`
-   **주문 내역 조회**: `POST /api/v1/orders/`
-   **주문 상세 조회**: `GET /api/v1/orders/{orderCode}`

자세한 API 명세는 Swagger UI (`/swagger-ui.html`)를 통해 확인할 수 있습니다.

## 4. 실행 방법

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

## 5. Docker Compose 실행

`docker-compose-order.yml` 파일을 사용하여 Docker 컨테이너로 서비스를 실행할 수 있습니다.

```bash
docker-compose -f docker-compose-order.yml up -d
```