package com.parkmate.reservationservice.kafka.consumer.userparkinghistory;

import com.parkmate.reservationservice.reservation.application.dispatcher.EventDispatcher;
import com.parkmate.reservationservice.reservation.application.handler.EventHandler;
import com.parkmate.reservationservice.reservation.event.userparkinghistory.HistoryType;
import com.parkmate.reservationservice.reservation.event.userparkinghistory.UserParkingHistoryEvent;
import com.parkmate.reservationservice.kafka.constant.KafkaTopics;
import com.parkmate.reservationservice.reservation.application.ReservationService;
import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserParkingHistoryConsumer {

    private final EventDispatcher eventDispatcher;

    @KafkaListener(
            topics = KafkaTopics.USER_PARKING_HISTORY,
            containerFactory = "userParkingHistoryContainerFactory"
    )
    public void consumeUserParkingHistory(UserParkingHistoryEvent event) {

        EventHandler eventHandler = eventDispatcher.dispatch(event);
        eventHandler.handle(event);
    }
}
