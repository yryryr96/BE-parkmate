package com.parkmate.reservationservice.reservation.application.dispatcher;

import com.parkmate.reservationservice.common.exception.BaseException;
import com.parkmate.reservationservice.reservation.application.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.parkmate.reservationservice.common.response.ResponseStatus.NOT_FOUND_EVENT_DISPATCHER;

@Component
@RequiredArgsConstructor
public class EventDispatcher {

    private final List<EventHandler<?>> handlers;

    @SuppressWarnings("unchecked")
    public EventHandler dispatch(Object event) {
        for (EventHandler handler : handlers) {
            if (handler.supports(event)) {
                return handler;
            }
        }

        throw new BaseException(NOT_FOUND_EVENT_DISPATCHER);
    }
}
