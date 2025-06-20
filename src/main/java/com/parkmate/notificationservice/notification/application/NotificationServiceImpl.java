package com.parkmate.notificationservice.notification.application;

import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.NotificationEventDispatcher;
import com.parkmate.notificationservice.notification.dto.response.NotificationResponseDto;
import com.parkmate.notificationservice.notification.event.NotificationEvent;
import com.parkmate.notificationservice.notification.infrastructure.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Mono<Void> create(Notification notification) {
        return notificationRepository.save(notification).then();
    }

    @Override
    public Flux<NotificationResponseDto> getNotificationsByReceiverUuid(String receiverUuid) {
        return null;
    }
}
