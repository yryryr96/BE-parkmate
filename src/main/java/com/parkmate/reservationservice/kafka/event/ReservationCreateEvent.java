package com.parkmate.reservationservice.kafka.event;

import com.parkmate.reservationservice.reservation.domain.Reservation;
import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationCreateEvent {

    private String reservationUuid;
    private String parkingLotUuid;
    private Long parkingSpotId;
    private String hostUuid;
    private String userUuid;
    private ReservationStatus status;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Builder
    private ReservationCreateEvent(String reservationUuid,
                                  String parkingLotUuid,
                                  Long parkingSpotId,
                                  String hostUuid,
                                  String userUuid,
                                  ReservationStatus status,
                                  LocalDateTime startDateTime,
                                  LocalDateTime endDateTime) {
        this.reservationUuid = reservationUuid;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingSpotId = parkingSpotId;
        this.hostUuid = hostUuid;
        this.userUuid = userUuid;
        this.status = status;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static ReservationCreateEvent from(String hostUuid, Reservation reservation) {
        return ReservationCreateEvent.builder()
                .reservationUuid(reservation.getReservationCode())
                .parkingLotUuid(reservation.getParkingLotUuid())
                .parkingSpotId(reservation.getParkingSpotId())
                .hostUuid(hostUuid)
                .userUuid(reservation.getUserUuid())
                .status(reservation.getStatus())
                .startDateTime(reservation.getEntryTime())
                .endDateTime(reservation.getExitTime())
                .build();
    }
}
