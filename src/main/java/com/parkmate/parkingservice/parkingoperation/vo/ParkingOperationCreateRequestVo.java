package com.parkmate.parkingservice.parkingoperation.vo;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ParkingOperationCreateRequestVo {

    private LocalDate operationDate;
    private LocalDateTime validStartTime;
    private LocalDateTime validEndTime;
    private int baseIntervalMinutes;
    private int baseFee;
    private int extraIntervalMinutes;
    private int extraFee;
    private double discountRate;
}
