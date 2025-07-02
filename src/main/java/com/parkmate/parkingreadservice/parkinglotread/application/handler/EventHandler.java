package com.parkmate.parkingreadservice.parkinglotread.application.handler;

public interface EventHandler<T> {

    boolean supports(Object event);

    void handle(T event);
}
