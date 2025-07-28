package com.parkmate.parkmateorderservice.order.application.dispatcher;

import com.parkmate.parkmateorderservice.common.exception.BaseException;
import com.parkmate.parkmateorderservice.order.application.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.parkmate.parkmateorderservice.common.response.ResponseStatus.NOT_FOUND_EVENT_DISPATCHER;


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

        throw new BaseException(NOT_FOUND_EVENT_DISPATCHER);
    }
}
