package com.parkmate.notificationservice.notification.application;

import com.parkmate.notificationservice.notification.dto.NotificationEventDto;

public interface NotificationService {

    void createNotification(NotificationEventDto notificationEventDto);
}
