package com.parkmate.parkingservice.parkingoperation.vo.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ParkingOperationUpdateRequestVo {

    private LocalDateTime validStartTime;
    private LocalDateTime validEndTime;
    private int baseIntervalMinutes;
    private int baseFee;
    private int extraIntervalMinutes;
    private int extraFee;
    private double discountRate;
}
