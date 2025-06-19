package com.parkmate.parkingservice.parkingoperation.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ParkingOperationResponseVo {

    private String parkingOperationUuid;
    private String parkingLotUuid;
    private LocalDateTime operationDate;
    private LocalDateTime validStartTime;
    private LocalDateTime validEndTime;
    private int baseIntervalMinutes;
    private int baseFee;
    private int extraIntervalMinutes;
    private int extraFee;
    private double discountRate;

    @Builder
    private ParkingOperationResponseVo(String parkingOperationUuid,
                                       String parkingLotUuid,
                                       LocalDateTime operationDate,
                                       LocalDateTime validStartTime,
                                       LocalDateTime validEndTime,
                                       int baseIntervalMinutes,
                                       int baseFee,
                                       int extraIntervalMinutes,
                                       int extraFee,
                                       double discountRate) {
        this.parkingOperationUuid = parkingOperationUuid;
        this.parkingLotUuid = parkingLotUuid;
        this.operationDate = operationDate;
        this.validStartTime = validStartTime;
        this.validEndTime = validEndTime;
        this.baseIntervalMinutes = baseIntervalMinutes;
        this.baseFee = baseFee;
        this.extraIntervalMinutes = extraIntervalMinutes;
        this.extraFee = extraFee;
        this.discountRate = discountRate;
    }
}
