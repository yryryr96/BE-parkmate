package com.parkmate.parkingservice.kafka.event;

import com.parkmate.parkingservice.parkingoperation.domain.ParkingOperation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OperationCreatedEvent {

    private String parkingLotUuid;
    private LocalDate operationDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int baseIntervalMinutes;
    private int baseFee;
    private int extraIntervalMinutes;
    private int extraFee;
    private double discountRate;

    @Builder
    private OperationCreatedEvent(String parkingLotUuid,
                                 LocalDate operationDate,
                                 LocalDateTime startTime,
                                 LocalDateTime endTime,
                                 int baseIntervalMinutes,
                                 int baseFee,
                                 int extraIntervalMinutes,
                                 int extraFee,
                                 double discountRate) {
        this.parkingLotUuid = parkingLotUuid;
        this.operationDate = operationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.baseIntervalMinutes = baseIntervalMinutes;
        this.baseFee = baseFee;
        this.extraIntervalMinutes = extraIntervalMinutes;
        this.extraFee = extraFee;
        this.discountRate = discountRate;
    }

    public static OperationCreatedEvent from(ParkingOperation operation) {
        return OperationCreatedEvent.builder()
                .parkingLotUuid(operation.getParkingLotUuid())
                .operationDate(operation.getOperationDate())
                .startTime(operation.getValidStartTime())
                .endTime(operation.getValidEndTime())
                .baseIntervalMinutes(operation.getBaseIntervalMinutes())
                .baseFee(operation.getBaseFee())
                .extraIntervalMinutes(operation.getExtraIntervalMinutes())
                .extraFee(operation.getExtraFee())
                .discountRate(operation.getDiscountRate())
                .build();
    }
}
