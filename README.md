# Parkmate 주차 정보 조회 서비스 (Read Service)

Parkmate 프로젝트의 주차 정보 조회 기능을 전담하는 마이크로서비스입니다.

## 1. 핵심 역할 및 아키텍처

본 서비스는 **CQRS (Command and Query Responsibility Segregation) 패턴의 Read 서비스** 역할을 수행합니다. 데이터 조회 요청만을 처리하여, 쓰기(Command) 역할을 담당하는 `parkmate-parking-service`와 책임을 분리함으로써 시스템의 성능과 확장성을 높입니다.

### 데이터 동기화 및 처리 흐름

1.  **이벤트 발행 (parkmate-parking-service)**:
    `parkmate-parking-service`에서 주차장 정보(생성, 수정, 삭제 등)가 변경될 때마다, 해당 변경 사항을 Kafka 토픽으로 도메인 이벤트를 발행합니다.

2.  **이벤트 구독 및 소비 (parkmate-parking-read-service)**:
    `parkmate-parking-read-service`는 Kafka의 특정 토픽을 구독(Subscribe)하고, 발행된 주차장 관련 이벤트를 실시간으로 소비합니다.

3.  **데이터베이스 업데이트 (MongoDB)**:
    소비된 이벤트의 내용에 따라 `parkmate-parking-read-service`는 자신의 전용 데이터 저장소인 MongoDB를 업데이트합니다. 예를 들어, 새로운 주차장 생성 이벤트가 오면 MongoDB에 새로운 주차장 문서를 추가하고, 주차장 정보 변경 이벤트가 오면 해당 주차장 문서를 업데이트합니다. 이 과정을 통해 Read 서비스의 데이터는 항상 최신 상태를 유지합니다.

4.  **성능 최적화 (Look-Aside Caching with Redis)**:
    사용자로부터 주차장 조회 요청이 들어오면, 본 서비스는 먼저 Redis 캐시를 확인합니다.
    -   **Cache-Hit**: 요청된 데이터가 Redis에 존재하면, 캐시된 데이터를 즉시 반환하여 응답 속도를 극대화합니다.
    -   **Cache-Miss**: 요청된 데이터가 Redis에 없으면, MongoDB에서 데이터를 조회합니다. 조회된 데이터는 Redis에 저장된 후 사용자에게 응답됩니다. 이는 다음 동일한 요청 시 캐시를 활용할 수 있도록 하여 반복적인 조회 요청에 대한 응답 속도를 크게 향상시킵니다.
    -   **네트워크 통신 최소화**: Redis와의 통신 시 `MGET`, `MSET` 등과 같은 멀티 키(Multi-key) 명령어를 적극적으로 활용하여, 여러 데이터를 한 번의 네트워크 왕복으로 처리함으로써 네트워크 오버헤드를 최소화하고 전체적인 처리량을 향상시킵니다.
    -   **지리 공간 데이터 처리 (Redis GEO)**: 주변 주차장 검색, 특정 영역 내 주차장 검색 등 지리 공간 기반 쿼리에는 Redis의 GEO 기능을 활용합니다. 이를 통해 빠르고 효율적인 위치 기반 검색을 제공합니다.
    -   **MongoDB 검색 인덱스 최적화**: MongoDB Atlas Search (Full-Text Search) 인덱스를 활용하여 키워드 기반 주차장 검색의 성능을 최적화합니다.

### CQRS 패턴의 이점

-   **성능 향상**: 읽기 및 쓰기 작업을 분리하여 각 서비스의 부하를 줄이고, 독립적인 확장을 가능하게 합니다.
-   **유연성**: 읽기 모델과 쓰기 모델을 독립적으로 설계하고 최적화할 수 있습니다.
-   **확장성**: 트래픽이 많은 조회 요청에 대해 Read 서비스를 독립적으로 확장할 수 있습니다.

## 2. 기술 스택

-   **언어:** Java 17
-   **프레임워크:** Spring Boot 3.4.3, Spring Cloud 2024.0.1
-   **데이터베이스:**
    -   **MongoDB:** 주차장 조회 모델을 저장하는 주 데이터 저장소
    -   **Redis:** 응답 속도 향상을 위한 캐시 저장소
-   **메시징 큐:** Apache Kafka
-   **서비스 디스커버리:** Netflix Eureka
-   **API 문서:** Swagger (SpringDoc OpenAPI)
-   **빌드 도구:** Gradle

## 3. API 개요

본 서비스는 주차장 정보 조회를 위한 다양한 API를 제공합니다. 주요 API는 다음과 같습니다:

-   **주차장 상세 조회**: 특정 주차장의 고유 ID를 통해 상세 정보를 조회합니다.
-   **주차장 간단 정보 조회**: 특정 주차장의 고유 ID를 통해 간단한 정보를 조회합니다.
-   **주변 주차장 검색**: 현재 위치를 기반으로 주변 주차장 목록을 검색합니다.
-   **영역 내 주차장 검색**: 지도상의 특정 사각형 영역 내에 있는 주차장 목록을 검색합니다.
-   **주차장과의 거리 계산**: 사용자 위치와 특정 주차장 간의 거리를 계산합니다.
-   **키워드 기반 주차장 검색**: 주차장 이름 또는 주소를 키워드로 검색합니다.

자세한 API 명세는 Swagger UI (`/swagger-ui.html`)를 통해 확인할 수 있습니다.

## 4. 실행 방법

### 빌드

```bash
./gradlew build
```

### 실행

```bash
java -jar build/libs/parkmate-parking-read-service-0.0.1-SNAPSHOT.jar
```

### YAML 파일을 이용한 실행

Spring Boot 애플리케이션은 `application.yml` 또는 `application.properties` 파일을 통해 설정을 관리합니다. 다음은 `application.yml` 파일의 예시입니다.

**`src/main/resources/application.yml` 예시:**

```yaml
spring:
  application:
    name: parkmate-parking-read-service
  data:
    mongodb:
      uri: ${MONGODB_URI:mongodb://localhost:27017/parkmate_read_db}
  redis:
    host: ${REDIS_HOST:localhost}
    port: ${REDIS_PORT:6379}
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
    consumer:
      group-id: parking-read-group
      auto-offset-reset: earliest
server:
  port: ${SERVER_PORT:8082}
eureka:
  client:
    serviceUrl:
      defaultZone: ${EUREKA_DEFAULT_ZONE:http://localhost:8761/eureka/}
```

## 5. Docker Compose 실행

`docker-compose-parking-read.yml` 파일을 사용하여 Docker 컨테이너로 서비스를 실행할 수 있습니다.

```bash
docker-compose -f docker-compose-parking-read.yml up -d
```