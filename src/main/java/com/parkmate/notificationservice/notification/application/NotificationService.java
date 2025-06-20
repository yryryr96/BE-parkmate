package com.parkmate.notificationservice.notification.application;

import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.dto.response.NotificationResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotificationService {

    Mono<Void> create(Notification notification);

    Flux<NotificationResponseDto> getNotificationsByReceiverUuid(String receiverUuid);
}
