package com.parkmate.reservationservice.kafka.consumer.userparkinghistory;

import com.parkmate.reservationservice.kafka.constant.KafkaTopics;
import com.parkmate.reservationservice.reservation.application.dispatcher.EventDispatcher;
import com.parkmate.reservationservice.reservation.application.handler.EventHandler;
import com.parkmate.reservationservice.reservation.event.userparkinghistory.UserParkingHistoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserParkingHistoryConsumer {

    private final EventDispatcher eventDispatcher;

    @KafkaListener(
            topics = KafkaTopics.USER_PARKING_HISTORY,
            containerFactory = "userParkingHistoryContainerFactory"
    )
    public void consumeUserParkingHistory(ConsumerRecord<String, UserParkingHistoryEvent> record) {

        long offset = record.offset();
        int partition = record.partition();
        UserParkingHistoryEvent event = record.value();

        try {
            EventHandler eventHandler = eventDispatcher.dispatch(event);
            eventHandler.handle(event);
        } catch (Exception e) {
            log.error("[Consume Error] offset {} in partition {}: {}", offset, partition, e.getMessage(), e);
        }
    }
}
