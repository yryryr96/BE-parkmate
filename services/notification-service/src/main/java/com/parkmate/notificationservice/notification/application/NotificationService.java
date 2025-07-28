package com.parkmate.notificationservice.notification.application;

import com.parkmate.notificationservice.common.response.CursorPage;
import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.NotificationStatus;
import com.parkmate.notificationservice.notification.dto.request.NotificationDeleteRequestDto;
import com.parkmate.notificationservice.notification.dto.request.NotificationReadRequestDto;
import com.parkmate.notificationservice.notification.dto.request.NotificationsGetRequestDto;
import com.parkmate.notificationservice.notification.dto.response.NotificationReadResponseDto;
import com.parkmate.notificationservice.notification.dto.response.NotificationResponseDto;

import java.util.List;

public interface NotificationService {

    List<Notification> create(List<Notification> notifications);

    CursorPage<NotificationResponseDto> getNotificationsByReceiverUuid(NotificationsGetRequestDto notificationsGetRequestDto);

    void updateNotificationStatus(Notification notification, NotificationStatus status);

    NotificationReadResponseDto readNotificationById(NotificationReadRequestDto notificationReadRequestDto);

    void delete(NotificationDeleteRequestDto notificationDeleteRequestDto);

    long getUnreadNotificationCount(String receiverUuid);

    List<Notification> bulkInsert(List<Notification> notifications);
}
