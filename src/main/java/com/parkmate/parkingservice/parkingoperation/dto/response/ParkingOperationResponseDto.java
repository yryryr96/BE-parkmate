package com.parkmate.parkingservice.parkingoperation.dto.response;

import com.parkmate.parkingservice.parkingoperation.domain.ParkingOperation;
import com.parkmate.parkingservice.parkingoperation.vo.response.ParkingOperationResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ParkingOperationResponseDto {

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
    private ParkingOperationResponseDto(String parkingOperationUuid,
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

    public static ParkingOperationResponseDto from(ParkingOperation parkingOperation) {

        return ParkingOperationResponseDto.builder()
                .parkingOperationUuid(parkingOperation.getParkingOperationUuid())
                .parkingLotUuid(parkingOperation.getParkingLotUuid())
                .operationDate(parkingOperation.getOperationDate())
                .validStartTime(parkingOperation.getValidStartTime())
                .validEndTime(parkingOperation.getValidEndTime())
                .baseIntervalMinutes(parkingOperation.getBaseIntervalMinutes())
                .baseFee(parkingOperation.getBaseFee())
                .extraIntervalMinutes(parkingOperation.getExtraIntervalMinutes())
                .extraFee(parkingOperation.getExtraFee())
                .discountRate(parkingOperation.getDiscountRate())
                .build();
    }

    public ParkingOperationResponseVo toVo() {
        return ParkingOperationResponseVo.builder()
                .parkingOperationUuid(this.parkingOperationUuid)
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
