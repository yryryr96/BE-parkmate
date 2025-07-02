package com.parkmate.notificationservice.notification.domain;

import com.parkmate.notificationservice.notification.event.NotificationEvent;
import com.parkmate.notificationservice.notification.domain.processor.EventProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventDispatcher {

    private final List<EventProcessor<? extends NotificationEvent>> processors;

    @SuppressWarnings("unchecked")
    public EventProcessor<NotificationEvent> dispatch(NotificationEvent event) {

        return (EventProcessor<NotificationEvent>) processors.stream()
                .filter(processor -> processor.supports(event))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("지원하지 않는 이벤트 타입입니다: {}", event.getClass().getSimpleName());
                    return new IllegalArgumentException("지원하지 않는 이벤트 타입입니다: " + event.getClass().getSimpleName());
                });
    }
}



