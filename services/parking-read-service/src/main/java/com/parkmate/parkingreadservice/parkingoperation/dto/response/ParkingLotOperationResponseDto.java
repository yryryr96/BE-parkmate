package com.parkmate.parkingreadservice.parkingoperation.dto.response;

import com.parkmate.parkingreadservice.parkingoperation.domain.ParkingLotOperationRead;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ParkingLotOperationResponseDto {

    private LocalDateTime operationDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int baseIntervalMinutes;
    private int baseFee;
    private int extraIntervalMinutes;
    private int extraFee;
    private double discountRate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private ParkingLotOperationResponseDto(LocalDateTime operationDate,
                                           LocalDateTime startTime,
                                           LocalDateTime endTime,
                                           int baseIntervalMinutes,
                                           int baseFee,
                                           int extraIntervalMinutes,
                                           int extraFee,
                                           double discountRate,
                                           LocalDateTime createdAt,
                                           LocalDateTime updatedAt) {
        this.operationDate = operationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.baseIntervalMinutes = baseIntervalMinutes;
        this.baseFee = baseFee;
        this.extraIntervalMinutes = extraIntervalMinutes;
        this.extraFee = extraFee;
        this.discountRate = discountRate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ParkingLotOperationResponseDto from(ParkingLotOperationRead parkingLotOperationRead) {
        return ParkingLotOperationResponseDto.builder()
                .operationDate(parkingLotOperationRead.getOperationDate())
                .startTime(parkingLotOperationRead.getStartDateTime())
                .endTime(parkingLotOperationRead.getEndDateTime())
                .baseIntervalMinutes(parkingLotOperationRead.getBaseIntervalMinutes())
                .baseFee(parkingLotOperationRead.getBaseFee())
                .extraIntervalMinutes(parkingLotOperationRead.getExtraIntervalMinutes())
                .extraFee(parkingLotOperationRead.getExtraFee())
                .discountRate(parkingLotOperationRead.getDiscountRate())
                .createdAt(parkingLotOperationRead.getCreatedAt())
                .updatedAt(parkingLotOperationRead.getUpdatedAt())
                .build();
    }
}
