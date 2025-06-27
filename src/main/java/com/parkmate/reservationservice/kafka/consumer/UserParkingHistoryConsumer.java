package com.parkmate.reservationservice.kafka.consumer;

import com.parkmate.reservationservice.kafka.event.HistoryType;
import com.parkmate.reservationservice.kafka.event.UserParkingHistoryEvent;
import com.parkmate.reservationservice.kafka.properties.KafkaTopics;
import com.parkmate.reservationservice.reservation.application.ReservationService;
import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserParkingHistoryConsumer {

    private final ReservationService reservationService;

    @KafkaListener(
            topics = KafkaTopics.USER_PARKING_HISTORY,
            containerFactory = "userParkingHistoryContainerFactory"
    )
    public void consumeUserParkingHistory(UserParkingHistoryEvent event) {

        String reservationCode = event.getReservationCode();

        if (event.getType().equals(HistoryType.ENTRY)) {
            reservationService.changeStatus(reservationCode, ReservationStatus.IN_USE);
        } else {
            reservationService.changeStatus(reservationCode, ReservationStatus.COMPLETED);
        }
    }
}
