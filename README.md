# Parkmate 주차 서비스

Parkmate 프로젝트의 주차장 관련 기능을 담당하는 마이크로서비스입니다.

## 1. 기술 스택

- **언어:** Java 17
- **프레임워크:** Spring Boot 3.4.3, Spring Cloud 2024.0.1
- **데이터베이스:**
  - **JPA (MySQL):** 주차장, 주차면 등 핵심 도메인 데이터 관리
  - **MongoDB:** 주차장 반응(좋아요 등)과 같은 비정형 데이터 관리
  - **Redis:** 캐싱, 실시간 데이터 처리 등
- **메시징 큐:** Apache Kafka
- **서비스 디스커버리:** Netflix Eureka
- **선언적 HTTP 클라이언트:** OpenFeign
- **API 문서:** Swagger (SpringDoc OpenAPI)
- **빌드 도구:** Gradle

## 2. 아키텍처

본 서비스는 **CQRS (Command and Query Responsibility Segregation) 패턴의 Command 서비스** 역할을 수행합니다. 데이터 변경(생성, 수정, 삭제) 관련 책임을 가지며, 조회 기능은 `parkmate-parking-read-service`가 담당합니다.

내부적으로는 MSA(Microservice Architecture)를 따르며, 계층형 아키텍처(Presentation, Application, Domain, Infrastructure)를 적용하여 각 모듈의 독립성과 응집도를 높였습니다.

- **Eureka:** 서비스 디스커버리 서버에 자신을 등록하여 다른 서비스가 검색할 수 있도록 합니다.
- **OpenFeign:** `reservation-service` 등 다른 마이크로서비스와 선언적으로 HTTP 통신을 수행합니다.
- **Kafka:** 주차장 생성, 운영 정보 변경 등의 도메인 이벤트를 발행하여 다른 서비스와 비동기적으로 데이터를 교환합니다.
- **데이터 저장소 분리:**
  - **MySQL (JPA):** 정형화된 핵심 데이터를 저장합니다. (예: 주차장 정보, 주차면 정보)
  - **MongoDB:** 사용자의 반응과 같이 스키마 변경이 유연해야 하는 데이터를 저장합니다.
  - **Redis:** 빠른 읽기/쓰기가 필요한 데이터를 캐싱하여 성능을 향상시킵니다.

## 3. 주요 기능

- 주차장 정보 관리 (생성, 조회, 수정, 삭제)
- 주차면 정보 관리
- 주차장 옵션 및 이미지 관리
- 주차장 반응(좋아요/싫어요) 관리
- 주차장 운영 정보 관리

## 4. 실행 방법

### 빌드

```bash
./gradlew build
```

### 실행

```bash
java -jar build/libs/parkmate-parking-service-0.0.1-SNAPSHOT.jar
```

## 5. API 문서

애플리케이션 실행 후, 아래 URL을 통해 API 문서를 확인할 수 있습니다.

  - [http://localhost:8100/swagger-ui.html](http://localhost:8100/swagger-ui.html)

## 6. YML 설정

본 서비스는 `application.yml` 파일을 통해 주요 설정을 관리합니다.

```yaml
server:
  port: 8100

spring:
  application:
    name: parking-service
  data:
    mongodb:
      uri: mongodb+srv://<username>:<password>@<host>/<database>
      auto-index-creation: true
    redis:
      host: localhost
      port: 6379
  datasource:
    url: jdbc:mysql://localhost:3306/parking_db?useSSL=false&allowPublicKeyRetrieval=true
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
    bootstrap-servers: localhost:10000,localhost:10001,localhost:10002
    producer:
      acks: all
      retries: 3
      batch-size: 16384
      linger-ms: 1
      buffer-memory: 33554432

eureka:
  instance:
    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:8761/eureka

logging:
  level:
    org.springframework.data.mongodb.core.MongoTemplate: DEBUG
    com.parkmate.parkingservice.feign.ReservationClient: DEBUG

springdoc:
  swagger-ui:
    path: /swagger-ui.html
  api-docs:
    path: /v3/api-docs
```

**주요 설정:**

- **`server.port`**: 애플리케이션 포트 (기본값: `8100`)
- **`spring.data.mongodb.uri`**: MongoDB 연결 URI
- **`spring.data.redis.host/port`**: Redis 서버 호스트 및 포트
- **`spring.datasource.url/username/password`**: MySQL 데이터베이스 연결 정보
- **`spring.kafka.bootstrap-servers`**: Kafka 브로커 서버 주소
- **`eureka.client.service-url.defaultZone`**: Eureka 서버 주소

## 7. Docker Compose 실행

`docker-compose-parking.yml` 파일을 사용하여 Docker 컨테이너로 서비스를 실행할 수 있습니다.

```bash
docker-compose -f docker-compose-parking.yml up -d
```

**사전 조건:**

- Docker 및 Docker Compose가 설치되어 있어야 합니다.
- `/home/ubuntu/config/parking/application.yml` 경로에 설정 파일이 존재해야 합니다.
- `/home/ubuntu/env/.env.parking` 경로에 환경 변수 파일이 존재해야 합니다.
- `backend` Docker 네트워크가 생성되어 있어야 합니다.