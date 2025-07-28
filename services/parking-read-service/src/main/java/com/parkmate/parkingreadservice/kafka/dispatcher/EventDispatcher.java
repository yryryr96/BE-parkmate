package com.parkmate.parkingreadservice.kafka.dispatcher;

import com.parkmate.parkingreadservice.common.exception.BaseException;
import com.parkmate.parkingreadservice.common.exception.ResponseStatus;
import com.parkmate.parkingreadservice.kafka.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventDispatcher {

    private final List<EventHandler<?>> handlers;

    public EventHandler<?> dispatch(Object event) {
        for (EventHandler<?> handler : handlers) {
            if (handler.supports(event)) {
                return handler;
            }
        }

        throw new BaseException(ResponseStatus.NOT_FOUND_EVENT_DISPATCHER);
    }
}
