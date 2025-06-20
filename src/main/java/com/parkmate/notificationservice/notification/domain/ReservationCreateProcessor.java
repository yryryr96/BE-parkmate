package com.parkmate.notificationservice.notification.domain;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ReservationCreateProcessor implements ReservationEventProcessor {

    private final String FORMAT = "예약이 생성되었습니다. 예약 ID: %s, 사용자 UUID: %s, 주차장 UUID: %s, 차량 번호: %s";

    @Override
    public Mono<Boolean> supports(ReservationEventType reservationType) {
        return Mono.just(ReservationEventType.CREATED.equals(reservationType));
    }

    @Override
    public Mono<Notification> create(ReservationEvent event) {

        String message = String.format(FORMAT,
                event.getReservationUuid(),
                event.getUserUuid(),
                event.getParkingLotUuid(),
                event.getVehicleNumber());

        Notification notification = Notification.builder()
                .receiverUuid(reservationEvent.getUserUuid())
                .message(message)
                .build();

        return Mono.just(notification);
    }
}
