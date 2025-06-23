package com.parkmate.notificationservice.notification.domain;

import lombok.Getter;

@Getter
public enum NotificationStatus {

    PENDING("대기 중"),
    SENT("전송됨"),
    FAILED("전송 실패"),
    READ("읽음"),
    DELETED("삭제됨")
    ;

    private final String description;

    NotificationStatus(String description) {
        this.description = description;
    };
}
