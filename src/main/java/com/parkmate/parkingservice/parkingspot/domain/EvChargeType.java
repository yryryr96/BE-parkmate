package com.parkmate.parkingservice.parkingspot.domain;

import lombok.Getter;

@Getter
public enum EvChargeType {

    AC_SINGLE("AC단상"),
    DC_CHADEMO("DC차데모"),
    DC_COMBO("DC콤보"),
    AC_THREE_PHASE("AC3상");

    private final String description;

    EvChargeType(String description) {
        this.description = description;
    }
}
