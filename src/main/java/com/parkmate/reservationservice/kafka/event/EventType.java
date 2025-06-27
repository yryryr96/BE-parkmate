package com.parkmate.reservationservice.kafka.event;

import lombok.Getter;

@Getter
public enum EventType {

    CREATED("생성"),
    UPDATED("수정"),
    DELETED("삭제");

    private final String description;

    EventType(String description) {
        this.description = description;
    }
}
