package com.parkmate.userparkinghistoryservice.kafka.event;

import lombok.Getter;

@Getter
public enum EventType {

    CREATED("생성"),
    DELETED("삭제")
    ;

    private final String description;

    EventType(String description) {
        this.description = description;
    }
}
