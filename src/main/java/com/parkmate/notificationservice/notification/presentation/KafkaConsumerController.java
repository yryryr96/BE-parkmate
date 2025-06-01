package com.parkmate.notificationservice.notification.presentation;

import com.parkmate.notificationservice.notification.application.NotificationService;
import com.parkmate.notificationservice.notification.dto.NotificationEventDto;
import com.parkmate.notificationservice.notification.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaConsumerController {

    private final NotificationService notificationService;

    @KafkaListener(topics = "reservation.complete", groupId = "notification-service", containerFactory = "reservationCompleteEventListener")
    public void consumeReservationCompleteEvent(NotificationEvent notificationEvent) {
        log.info("Received message from reservation.complete topic: {}", notificationEvent);
        notificationService.createNotification(NotificationEventDto.from(notificationEvent));
    }
}
