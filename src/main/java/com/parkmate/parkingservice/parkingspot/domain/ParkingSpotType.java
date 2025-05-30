package com.parkmate.parkingservice.parkingspot.domain;

import lombok.Getter;

@Getter
public enum ParkingSpotType {

    COMPACT("소형차"),
    SMALL("경차"),
    STANDARD("중형차"),
    LARGE("대형차"),
    EV("전기차")
    ;

    private final String description;

    ParkingSpotType(String description) {
        this.description = description;
    }
}
