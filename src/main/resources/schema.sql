-- src/main/resources/schema.sql (for MySQL)

-- user_device_tokens 테이블 생성
CREATE TABLE IF NOT EXISTS user_device_tokens (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, -- MySQL의 자동 증가 기본 키
    user_uuid VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE, -- FCM 토큰은 고유하며 인덱스 역할
    created_at DATETIME NOT NULL,            -- DATETIME 사용 (TIMESTAMP 대신)
    updated_at DATETIME NOT NULL,
    INDEX idx_user_tokens_user_id (user_uuid) -- user_id로 조회 시 성능 향상을 위한 인덱스
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci; -- 한글 및 이모지 지원을 위해 utf8mb4 권장