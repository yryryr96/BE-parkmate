package com.parkmate.parkingreadservice.kafka.event.parkinglot;

import com.parkmate.parkingreadservice.parkingoperation.domain.ParkingLotOperationRead;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OperationCreateEvent {

    private String parkingLotUuid;
    private LocalDateTime operationDate;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int baseIntervalMinutes;
    private int baseFee;
    private int extraIntervalMinutes;
    private int extraFee;
    private double discountRate;

    public ParkingLotOperationRead toEntity() {
        return ParkingLotOperationRead.builder()
                .parkingLotUuid(parkingLotUuid)
                .operationDate(operationDate)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
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
