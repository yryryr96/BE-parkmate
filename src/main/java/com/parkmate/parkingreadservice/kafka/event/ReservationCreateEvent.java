package com.parkmate.parkingreadservice.kafka.event;

import com.parkmate.parkingreadservice.reservation.domain.ReservationRecord;
import com.parkmate.parkingreadservice.reservation.domain.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreateEvent {

    private String parkingLotUuid;
    private Long parkingSpotId;
    private ReservationStatus status;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public ReservationRecord toRecord() {
        return ReservationRecord.builder()
                .parkingLotUuid(parkingLotUuid)
                .parkingSpotId(parkingSpotId)
                .status(status)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }
}
