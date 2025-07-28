package com.parkmate.notificationservice.notificationsender;

import com.parkmate.notificationservice.notification.domain.Notification;

import java.util.concurrent.CompletableFuture;

public interface NotificationSender {

    CompletableFuture<Void> send(Notification notification);
}
