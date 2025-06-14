package com.parkmate.parkingreadservice.reservation.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reservation_records")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationRecord {

    @Id
    private String id;
    private String parkingLotUuid;
    private Long parkingSpotId;
    private ReservationStatus status;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Builder
    private ReservationRecord(String id,
                              String parkingLotUuid,
                              Long parkingSpotId,
                              ReservationStatus status,
                              LocalDateTime startDateTime,
                              LocalDateTime endDateTime) {
        this.id = id;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingSpotId = parkingSpotId;
        this.status = status;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
