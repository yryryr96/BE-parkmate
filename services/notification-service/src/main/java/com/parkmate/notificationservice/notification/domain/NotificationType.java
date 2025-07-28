package com.parkmate.notificationservice.notification.domain;

import lombok.Getter;

@Getter
public enum NotificationType {

    CHAT_MESSAGE("채팅 메시지"),
    EMPTY_SPOT_AVAILABLE("빈 주차 공간 알림"),
    RESERVATION_CREATED("예약 생성 알림"),
    RESERVATION_MODIFIED("예약 수정 알림"),
    RESERVATION_CANCELED("예약 취소 알림"),
    USER_PARKING_ENTRY("사용자 입차 알림"),
    PARKING_EXIT_REMINDER("주차장 퇴장 알림"),
    ;

    private final String description;

    NotificationType(String description) {
        this.description = description;
    }
}
