package com.parkmate.notificationservice.notification.domain.event;

import com.parkmate.notificationservice.notification.domain.NotificationType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReservationCreatedEvent extends NotificationEvent {

    private String reservationUuid;
    private String userUuid;
    private String parkingLotUuid;
    private Long parkingSpotId;

    protected ReservationCreatedEvent() {
        super(NotificationType.RESERVATION_CREATED);
    }

    @Builder
    private ReservationCreatedEvent(String reservationUuid, String userUuid,
                                   String parkingLotUuid, Long parkingSpotId) {

        super(NotificationType.RESERVATION_CREATED);
        this.reservationUuid = reservationUuid;
        this.userUuid = userUuid;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingSpotId = parkingSpotId;
    }
}
