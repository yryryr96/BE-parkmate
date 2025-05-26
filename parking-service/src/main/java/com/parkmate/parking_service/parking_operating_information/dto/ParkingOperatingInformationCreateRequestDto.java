package com.parkmate.parking_service.parking_operating_information.dto;

import com.parkmate.parking_service.parking_operating_information.entity.ParkingOperatingInformation;
import com.parkmate.parking_service.parking_operating_information.vo.ParkingOperatingInformationCreateRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ParkingOperatingInformationCreateRequestDto {

    private String parkingLotUuid;
    private LocalDateTime validStartTime;
    private LocalDateTime validEndTime;
    private int baseIntervalMinutes;
    private int baseFee;
    private int extraIntervalMinutes;
    private int extraFee;
    private double discountRate;

    @Builder
    private ParkingOperatingInformationCreateRequestDto(String parkingLotUuid,
                                                       LocalDateTime validStartTime,
                                                       LocalDateTime validEndTime,
                                                       int baseIntervalMinutes,
                                                       int baseFee,
                                                       int extraIntervalMinutes,
                                                       int extraFee,
                                                       double discountRate) {
        this.parkingLotUuid = parkingLotUuid;
        this.validStartTime = validStartTime;
        this.validEndTime = validEndTime;
        this.baseIntervalMinutes = baseIntervalMinutes;
        this.baseFee = baseFee;
        this.extraIntervalMinutes = extraIntervalMinutes;
        this.extraFee = extraFee;
        this.discountRate = discountRate;
    }

    public static ParkingOperatingInformationCreateRequestDto of(String parkingLotUuid,
                                                                 ParkingOperatingInformationCreateRequestVo parkingOperatingInformationCreateRequestVo) {
        return ParkingOperatingInformationCreateRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .validStartTime(parkingOperatingInformationCreateRequestVo.getValidStartTime())
                .validEndTime(parkingOperatingInformationCreateRequestVo.getValidEndTime())
                .baseIntervalMinutes(parkingOperatingInformationCreateRequestVo.getBaseIntervalMinutes())
                .baseFee(parkingOperatingInformationCreateRequestVo.getBaseFee())
                .extraIntervalMinutes(parkingOperatingInformationCreateRequestVo.getExtraIntervalMinutes())
                .extraFee(parkingOperatingInformationCreateRequestVo.getExtraFee())
                .discountRate(parkingOperatingInformationCreateRequestVo.getDiscountRate())
                .build();
    }

    public ParkingOperatingInformation toEntity() {
        return ParkingOperatingInformation.builder()
                .parkingLotUuid(this.parkingLotUuid)
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
