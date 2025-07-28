package com.parkmate.notificationservice.notification.event.userparkinghistory;

import com.parkmate.notificationservice.notification.domain.NotificationType;
import com.parkmate.notificationservice.notification.event.NotificationEvent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserParkingHistoryEvent extends NotificationEvent {

    private String userUuid;
    private String parkingSpotName;
    private HistoryType type;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    protected UserParkingHistoryEvent() {
        super(NotificationType.USER_PARKING_ENTRY);
    }

    @Builder
    public UserParkingHistoryEvent(NotificationType notificationType,
                                   String userUuid,
                                   String parkingSpotName,
                                   HistoryType type,
                                   LocalDateTime entryTime,
                                   LocalDateTime exitTime) {
        super(notificationType);
        this.userUuid = userUuid;
        this.parkingSpotName = parkingSpotName;
        this.type = type;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }
}
