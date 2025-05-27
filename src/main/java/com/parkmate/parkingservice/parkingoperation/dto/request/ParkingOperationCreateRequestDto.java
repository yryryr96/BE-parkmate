package com.parkmate.parkingservice.parkingoperation.dto.request;

import com.parkmate.parkingservice.parkingoperation.entity.ParkingOperation;
import com.parkmate.parkingservice.parkingoperation.vo.ParkingOperationCreateRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ParkingOperationCreateRequestDto {

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
    private ParkingOperationCreateRequestDto(String parkingLotUuid,
                                             LocalDate operationDate,
                                             LocalDateTime validStartTime,
                                             LocalDateTime validEndTime,
                                             int baseIntervalMinutes,
                                             int baseFee,
                                             int extraIntervalMinutes,
                                             int extraFee,
                                             double discountRate) {
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

    public static ParkingOperationCreateRequestDto of(String parkingLotUuid,
                                                      ParkingOperationCreateRequestVo parkingOperationCreateRequestVo) {
        return ParkingOperationCreateRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .operationDate(parkingOperationCreateRequestVo.getOperationDate())
                .validStartTime(parkingOperationCreateRequestVo.getValidStartTime())
                .validEndTime(parkingOperationCreateRequestVo.getValidEndTime())
                .baseIntervalMinutes(parkingOperationCreateRequestVo.getBaseIntervalMinutes())
                .baseFee(parkingOperationCreateRequestVo.getBaseFee())
                .extraIntervalMinutes(parkingOperationCreateRequestVo.getExtraIntervalMinutes())
                .extraFee(parkingOperationCreateRequestVo.getExtraFee())
                .discountRate(parkingOperationCreateRequestVo.getDiscountRate())
                .build();
    }

    public ParkingOperation toEntity() {
        return ParkingOperation.builder()
                .parkingLotUuid(this.parkingLotUuid)
                .operationDate(this.operationDate)
                .validStartTime(this.validStartTime)
                .validEndTime(this.validEndTime)
                .baseIntervalMinutes(this.baseIntervalMinutes)
                .baseFee(this.baseFee)
                .extraIntervalMinutes(this.extraIntervalMinutes)
                .extraFee(this.extraFee)
                .discountRate(this.discountRate)
                .build();
    }
}
