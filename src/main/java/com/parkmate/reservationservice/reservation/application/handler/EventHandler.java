package com.parkmate.reservationservice.reservation.application.handler;

public interface EventHandler<T> {

    boolean supports(T event);

    void handle(T event);
}
