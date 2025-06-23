package com.parkmate.notificationservice.notification.infrastructure;

import com.parkmate.notificationservice.common.response.CursorPage;
import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.dto.request.NotificationDeleteRequestDto;
import com.parkmate.notificationservice.notification.dto.request.NotificationReadRequestDto;
import com.parkmate.notificationservice.notification.dto.request.NotificationsGetRequestDto;

public interface NotificationCustomRepository {

    CursorPage<Notification> getNotificationsByReceiverUuid(NotificationsGetRequestDto notificationsGetRequestDto);

    Notification readNotification(NotificationReadRequestDto notificationReadRequestDto);

    void delete(NotificationDeleteRequestDto notificationDeleteRequestDto);
}
