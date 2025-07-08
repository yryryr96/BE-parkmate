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
  - `common`: 공통적으로 사용되는 유틸리티 및 예외 처리 클래스
  - `kafka`: Kafka 메시지 발행 및 구독 관련 로직
  - `reservation`: 예약 도메인 관련 핵심 로직 (엔티티, 레포지토리, 서비스, 컨트롤러)
- **Eureka:** 서비스 디스커버리 서버에 자신을 등록하여 다른 서비스가 검색할 수 있도록 합니다.
- **OpenFeign:** 다른 마이크로서비스와 선언적으로 HTTP 통신을 수행합니다.
- **Kafka:** 예약 생성, 취소 등의 도메인 이벤트를 발행하여 다른 서비스와 비동기적으로 데이터를 교환합니다.

## 3. 주요 기능

- 주차장 예약 생성, 조회, 수정, 취소
- 예약 가능 시간 조회
- 사용자의 예약 내역 조회

## 4. 성능 최적화 및 개선 사항

본 서비스는 사용자에게 빠르고 효율적인 예약 경험을 제공하기 위해 다음과 같은 성능 최적화 및 개선을 적용했습니다.

-   **시간 복잡도 개선 (Map 활용)**: 기존에 중첩된 `for` 루프(이중 `for` 문)를 사용하여 발생할 수 있는 시간 복잡도 문제를 해결하기 위해, `Map` 자료구조를 적극적으로 활용하여 데이터 조회 및 처리를 최적화했습니다. 이를 통해 대량의 데이터 처리 시 발생하는 성능 저하를 방지하고, 로직의 효율성을 크게 향상시켰습니다.

-   **커서 기반 페이지네이션 도입**: `offset` 기반 페이지네이션의 한계점(예: 대량의 데이터 조회 시 성능 저하, 데이터 중복 또는 누락 가능성)을 극복하기 위해 커서 기반 페이지네이션을 도입했습니다. 이는 마지막으로 조회된 항목의 커서(예: ID 또는 타임스탬프)를 사용하여 다음 페이지를 조회하는 방식으로, 대규모 데이터셋에서도 일관되고 효율적인 페이지네이션을 가능하게 합니다.


## 5. 실행 방법

### 빌드

```bash
./gradlew build
```

### 실행

```bash
java -jar build/libs/parkmate-reservation-service-0.0.1-SNAPSHOT.jar
```

## 6. API 문서

애플리케이션 실행 후, 아래 URL을 통해 API 문서를 확인할 수 있습니다.

- [http://localhost:8200/swagger-ui.html](http://localhost:8200/swagger-ui.html)

## 7. YML 설정

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

## 8. Docker Compose 실행

`docker-compose-reservation.yml` 파일을 사용하여 Docker 컨테이너로 서비스를 실행할 수 있습니다.

```bash
docker-compose -f docker-compose-reservation.yml up -d
```

**사전 조건:**

- Docker 및 Docker Compose가 설치되어 있어야 합니다.
- `/home/ubuntu/config/reservation/application.yml` 경로에 설정 파일이 존재해야 합니다.
- `/home/ubuntu/env/.env.reservation` 경로에 환경 변수 파일이 존재해야 합니다.
- `backend` Docker 네트워크가 생성되어 있어야 합니다.
