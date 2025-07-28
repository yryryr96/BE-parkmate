package com.parkmate.notificationservice.notification.event;

import com.parkmate.notificationservice.notification.domain.NotificationType;
import lombok.Getter;

@Getter
public abstract class NotificationEvent {

    private final NotificationType notificationType;

    protected NotificationEvent(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
