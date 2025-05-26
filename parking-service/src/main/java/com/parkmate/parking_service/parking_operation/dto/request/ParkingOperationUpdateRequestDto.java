package com.parkmate.parking_service.parking_operation.dto.request;

import com.parkmate.parking_service.parking_operation.vo.request.ParkingOperationUpdateRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ParkingOperationUpdateRequestDto {

    private String parkingOperationUuid;
    private String parkingLotUuid;
    private LocalDateTime validStartTime;
    private LocalDateTime validEndTime;
    private int baseIntervalMinutes;
    private int baseFee;
    private int extraIntervalMinutes;
    private int extraFee;
    private double discountRate;

    @Builder
    private ParkingOperationUpdateRequestDto(String parkingOperationUuid,
                                            String parkingLotUuid,
                                            LocalDateTime validStartTime,
                                            LocalDateTime validEndTime,
                                            int baseIntervalMinutes,
                                            int baseFee,
                                            int extraIntervalMinutes,
                                            int extraFee,
                                            double discountRate) {
        this.parkingOperationUuid = parkingOperationUuid;
        this.parkingLotUuid = parkingLotUuid;
        this.validStartTime = validStartTime;
        this.validEndTime = validEndTime;
        this.baseIntervalMinutes = baseIntervalMinutes;
        this.baseFee = baseFee;
        this.extraIntervalMinutes = extraIntervalMinutes;
        this.extraFee = extraFee;
        this.discountRate = discountRate;
    }

    public static ParkingOperationUpdateRequestDto of(String parkingLotUuid,
                                                      String operationUuid,
                                                      ParkingOperationUpdateRequestVo parkingOperationCreateRequestVo) {
        return ParkingOperationUpdateRequestDto.builder()
                .parkingOperationUuid(operationUuid)
                .parkingLotUuid(parkingLotUuid)
                .validStartTime(parkingOperationCreateRequestVo.getValidStartTime())
                .validEndTime(parkingOperationCreateRequestVo.getValidEndTime())
                .baseIntervalMinutes(parkingOperationCreateRequestVo.getBaseIntervalMinutes())
                .baseFee(parkingOperationCreateRequestVo.getBaseFee())
                .extraIntervalMinutes(parkingOperationCreateRequestVo.getExtraIntervalMinutes())
                .extraFee(parkingOperationCreateRequestVo.getExtraFee())
                .discountRate(parkingOperationCreateRequestVo.getDiscountRate())
                .build();
    }
}
