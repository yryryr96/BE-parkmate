package com.parkmate.parkingservice.parkinglot.domain;

import lombok.Getter;

@Getter
public enum ParkingLotType {

    PUBLIC("공영주차장"),
    PRIVATE("민영주차장"),
    COMMERCIAL("상업지주차장");

    private final String description;

    ParkingLotType(String description) {
        this.description = description;
    }
}
