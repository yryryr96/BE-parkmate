package com.parkmate.parkingreadservice.kafka.event;

import com.parkmate.parkingreadservice.parkingoperation.domain.ParkingLotOperationRead;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OperationCreateEvent {

    private String parkingLotUuid;
    private LocalDate operationDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int baseIntervalMinutes;
    private int baseFee;
    private int extraIntervalMinutes;
    private int extraFee;
    private double discountRate;

    public ParkingLotOperationRead toEntity() {
        return ParkingLotOperationRead.builder()
                .parkingLotUuid(parkingLotUuid)
                .operationDate(operationDate)
                .startTime(startTime)
                .endTime(endTime)
                .baseIntervalMinutes(baseIntervalMinutes)
                .baseFee(baseFee)
                .extraIntervalMinutes(extraIntervalMinutes)
                .extraFee(extraFee)
                .discountRate(discountRate)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
