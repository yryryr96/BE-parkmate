package com.parkmate.notificationservice.notification.domain.processor;

import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.event.NotificationEvent;

import java.util.concurrent.CompletableFuture;

public interface EventProcessor<E extends NotificationEvent> {

    boolean supports(NotificationEvent event);

    CompletableFuture<Notification> create(E event);
}
