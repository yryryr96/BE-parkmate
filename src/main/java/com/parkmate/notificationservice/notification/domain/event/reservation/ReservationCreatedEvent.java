package com.parkmate.notificationservice.notification.domain.event.reservation;

import com.parkmate.notificationservice.notification.domain.NotificationType;
import com.parkmate.notificationservice.notification.domain.event.NotificationEvent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationCreatedEvent extends NotificationEvent {

    private String reservationCode;
    private String userUuid;
    private String hostUuid;
    private String parkingLotUuid;
    private String parkingLotName;
    private Long parkingSpotId;
    private String parkingSpotName;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    protected ReservationCreatedEvent() {
        super(NotificationType.RESERVATION_CREATED);
    }

    @Builder
    private ReservationCreatedEvent(NotificationType notificationType,
                                    String reservationCode,
                                    String userUuid,
                                    String hostUuid,
                                    String parkingLotUuid,
                                    String parkingLotName,
                                    Long parkingSpotId,
                                    String parkingSpotName,
                                    String vehicleNumber,
                                    LocalDateTime entryTime,
                                    LocalDateTime exitTime) {
        super(notificationType);
        this.reservationCode = reservationCode;
        this.userUuid = userUuid;
        this.hostUuid = hostUuid;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingLotName = parkingLotName;
        this.parkingSpotId = parkingSpotId;
        this.parkingSpotName = parkingSpotName;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }
}
