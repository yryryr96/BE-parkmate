package com.parkmate.reservationservice.kafka.event;

import com.parkmate.reservationservice.reservation.domain.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationEvent {

    private String reservationUuid;
    private String parkingLotUuid;
    private Long parkingSpotId;
    private String hostUuid;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Builder
    private ReservationEvent(String reservationUuid,
                            String parkingLotUuid,
                            Long parkingSpotId,
                            String hostUuid,
                            LocalDateTime startDateTime,
                            LocalDateTime endDateTime) {
        this.reservationUuid = reservationUuid;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingSpotId = parkingSpotId;
        this.hostUuid = hostUuid;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static ReservationEvent from(String hostUuid, Reservation reservation) {
        return ReservationEvent.builder()
                .reservationUuid(reservation.getReservationCode())
                .parkingLotUuid(reservation.getParkingLotUuid())
                .parkingSpotId(reservation.getParkingSpotId())
                .hostUuid(hostUuid)
                .startDateTime(reservation.getEntryTime())
                .endDateTime(reservation.getExitTime())
                .build();
    }
}
