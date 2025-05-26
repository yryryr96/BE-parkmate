package com.parkmate.parking_service.parking_operation.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "parking_operations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingOperation {

    @Id
    private String parkingOperationUuid;
    private String parkingLotUuid;
    private LocalDate operationDate;
    private LocalDateTime validStartTime;
    private LocalDateTime validEndTime;
    private int baseIntervalMinutes;
    private int baseFee;
    private int extraIntervalMinutes;
    private int extraFee;
    private double discountRate;

    @Builder
    private ParkingOperation(String parkingOperationUuid,
                             String parkingLotUuid,
                             LocalDate operationDate,
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
