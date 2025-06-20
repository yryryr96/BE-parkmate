package com.parkmate.notificationservice.notification.domain;

import reactor.core.publisher.Mono;

public interface ReservationEventProcessor {

    Mono<Boolean> supports(ReservationEventType reservationType);

    Mono<Notification> create(ReservationEvent reservationEvent);
}
