package com.parkmate.notificationservice.notification.domain;

import lombok.Getter;

@Getter
public enum ReservationEventType {

    CREATED,
    MODIFIED,
    CANCELED,
    COMPLETED,
    EXPIRED,
    ;
}
