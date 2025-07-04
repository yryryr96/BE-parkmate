package com.parkmate.reservationservice.reservation.application.handler;

public interface EventHandler<T> {

    boolean supports(Object event);

    void handle(T event);
}
