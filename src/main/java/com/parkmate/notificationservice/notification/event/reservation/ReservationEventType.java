package com.parkmate.notificationservice.notification.event.reservation;

import lombok.Getter;

@Getter
public enum ReservationEventType {

    CREATED("예약 생성"),
    CONFIRMED("예약 확정"),
    UPDATED("예약 수정")
    ;

    private final String description;

    ReservationEventType(String description) {
        this.description = description;
    }
}
