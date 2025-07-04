package com.parkmate.reservationservice.reservation.event.reservation;

import com.parkmate.reservationservice.reservation.domain.Reservation;
import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationEvent {

    private ReservationEventType eventType;
    private String reservationCode;
    private String parkingLotUuid;
    private String parkingLotName;
    private Long parkingSpotId;
    private String parkingSpotName;
    private String hostUuid;
    private String userUuid;
    private String vehicleNumber;
    private long amount;
    private ReservationStatus status;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private LocalDateTime timestamp;

    @Builder
    private ReservationEvent(ReservationEventType eventType,
            String reservationCode,
                             String parkingLotUuid,
                             String parkingLotName,
                             Long parkingSpotId,
                             String parkingSpotName,
                             String hostUuid,
                             String userUuid,
                             String vehicleNumber,
                             long amount,
                             ReservationStatus status,
                             LocalDateTime entryTime,
                             LocalDateTime exitTime,
                             LocalDateTime timestamp) {
        this.eventType = eventType;
        this.reservationCode = reservationCode;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingLotName = parkingLotName;
        this.parkingSpotId = parkingSpotId;
        this.parkingSpotName = parkingSpotName;
        this.hostUuid = hostUuid;
        this.userUuid = userUuid;
        this.status = status;
        this.amount = amount;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.timestamp = timestamp;
    }

    public static ReservationEvent from(String hostUuid,
                                        Reservation reservation) {
        return ReservationEvent.builder()
                .eventType(ReservationEventType.CREATED)
                .reservationCode(reservation.getReservationCode())
                .parkingLotUuid(reservation.getParkingLotUuid())
                .parkingLotName(reservation.getParkingLotName())
                .parkingSpotId(reservation.getParkingSpotId())
                .parkingSpotName(reservation.getParkingSpotName())
                .hostUuid(hostUuid)
                .userUuid(reservation.getUserUuid())
                .status(reservation.getStatus())
                .vehicleNumber(reservation.getVehicleNumber())
                .amount(reservation.getAmount())
                .entryTime(reservation.getEntryTime())
                .exitTime(reservation.getExitTime())
                .timestamp(reservation.getCreatedAt())
                .build();
    }

    public static ReservationEvent from(Reservation reservation) {
        return ReservationEvent.builder()
                .eventType(ReservationEventType.UPDATED)
                .reservationCode(reservation.getReservationCode())
                .parkingLotUuid(reservation.getParkingLotUuid())
                .parkingLotName(reservation.getParkingLotName())
                .parkingSpotId(reservation.getParkingSpotId())
                .parkingSpotName(reservation.getParkingSpotName())
                .userUuid(reservation.getUserUuid())
                .status(reservation.getStatus())
                .vehicleNumber(reservation.getVehicleNumber())
                .amount(reservation.getAmount())
                .entryTime(reservation.getEntryTime())
                .exitTime(reservation.getExitTime())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
