package com.parkmate.parking_service.parking_operating_information.vo;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ParkingOperatingInformationCreateRequestVo {

    private LocalDateTime validStartTime;
    private LocalDateTime validEndTime;
    private int baseIntervalMinutes;
    private int baseFee;
    private int extraIntervalMinutes;
    private int extraFee;
    private double discountRate;
}
