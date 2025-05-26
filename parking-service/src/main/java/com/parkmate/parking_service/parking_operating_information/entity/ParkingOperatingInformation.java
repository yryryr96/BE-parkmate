package com.parkmate.parking_service.parking_operating_information.entity;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "parking_operating_information")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingOperatingInformation {

    @Id
    private String parkingOperatingInformationUuid;
    private String parkingLotUuid;
    private LocalDateTime validStartTime;
    private LocalDateTime validEndTime;
    private int baseIntervalMinutes;
    private int baseFee;
    private int extraIntervalMinutes;
    private int extraFee;
    private double discountRate;

    @Builder
    private ParkingOperatingInformation(String parkingOperatingInformationUuid,
                                       String parkingLotUuid,
                                       LocalDateTime validStartTime,
                                       LocalDateTime validEndTime,
                                       int baseIntervalMinutes,
                                       int baseFee,
                                       int extraIntervalMinutes,
                                       int extraFee,
                                       double discountRate) {
        this.parkingOperatingInformationUuid = parkingOperatingInformationUuid;
        this.parkingLotUuid = parkingLotUuid;
        this.validStartTime = validStartTime;
        this.validEndTime = validEndTime;
        this.baseIntervalMinutes = baseIntervalMinutes;
        this.baseFee = baseFee;
        this.extraIntervalMinutes = extraIntervalMinutes;
        this.extraFee = extraFee;
        this.discountRate = discountRate;
    }
}
