package com.parkmate.parkingservice.parkingoperation.dto.request;

import com.parkmate.parkingservice.parkingoperation.domain.ParkingOperation;
import com.parkmate.parkingservice.parkingoperation.vo.request.ParkingOperationRegisterRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ParkingOperationRegisterRequestDto {

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
    private ParkingOperationRegisterRequestDto(String parkingLotUuid,
                                               LocalDateTime operationDate,
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

    public static ParkingOperationRegisterRequestDto of(String parkingLotUuid,
                                                        ParkingOperationRegisterRequestVo parkingOperationRegisterRequestVo) {
        return ParkingOperationRegisterRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .operationDate(parkingOperationRegisterRequestVo.getOperationDate().atStartOfDay())
                .validStartTime(parkingOperationRegisterRequestVo.getValidStartTime())
                .validEndTime(parkingOperationRegisterRequestVo.getValidEndTime())
                .baseIntervalMinutes(parkingOperationRegisterRequestVo.getBaseIntervalMinutes())
                .baseFee(parkingOperationRegisterRequestVo.getBaseFee())
                .extraIntervalMinutes(parkingOperationRegisterRequestVo.getExtraIntervalMinutes())
                .extraFee(parkingOperationRegisterRequestVo.getExtraFee())
                .discountRate(parkingOperationRegisterRequestVo.getDiscountRate())
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
