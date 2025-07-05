package com.parkmate.notificationservice.notification.event.reservation;

import com.parkmate.notificationservice.notification.domain.NotificationType;
import com.parkmate.notificationservice.notification.event.NotificationEvent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationEvent extends NotificationEvent {

    private ReservationEventType eventType;
    private String reservationCode;
    private String userUuid;
    private String parkingLotUuid;
    private String parkingLotName;
    private Long parkingSpotId;
    private String parkingSpotName;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    protected ReservationEvent() {
        super(NotificationType.RESERVATION_CREATED);
    }

    @Builder
    private ReservationEvent(NotificationType notificationType,
                             ReservationEventType eventType,
                             String reservationCode,
                             String userUuid,
                             String parkingLotUuid,
                             String parkingLotName,
                             Long parkingSpotId,
                             String parkingSpotName,
                             String vehicleNumber,
                             LocalDateTime entryTime,
                             LocalDateTime exitTime) {
        super(notificationType);
        this.eventType = eventType;
        this.reservationCode = reservationCode;
        this.userUuid = userUuid;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingLotName = parkingLotName;
        this.parkingSpotId = parkingSpotId;
        this.parkingSpotName = parkingSpotName;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }
}
