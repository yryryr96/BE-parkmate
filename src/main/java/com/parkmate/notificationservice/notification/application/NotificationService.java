package com.parkmate.notificationservice.notification.application;

import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.NotificationStatus;
import com.parkmate.notificationservice.notification.dto.response.NotificationResponseDto;

import java.util.List;

public interface NotificationService {

    List<Notification> create(List<Notification> notifications);

    List<NotificationResponseDto> getNotificationsByReceiverUuid(String receiverUuid);

    void updateNotificationStatus(Notification notification, NotificationStatus status);
}
