package com.parkmate.userparkinghistoryservice.userpakinrghistory.domain;

import lombok.Getter;

@Getter
public enum HistoryType {

    ENTRY("입차"),
    EXIT("출차")
    ;

    private final String description;

    HistoryType(String description) {
        this.description = description;
    }
}
