package com.parkmate.notificationservice.kafka.consumer;

import com.parkmate.notificationservice.kafka.constant.KafkaTopics;
import com.parkmate.notificationservice.notification.application.NotificationEventHandler;
import com.parkmate.notificationservice.notification.domain.event.userparkinghistory.UserParkingHistoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserParkingHistoryConsumer {

    private final NotificationEventHandler eventHandler;

    @KafkaListener(
            topics = KafkaTopics.USER_PARKING_HISTORY,
            containerFactory = "userParkingHistoryContainerFactory"
    )
    public void consumeUserParkingHistoryEvent(List<UserParkingHistoryEvent> events) {
        eventHandler.handleEvent(events);
    }
}
