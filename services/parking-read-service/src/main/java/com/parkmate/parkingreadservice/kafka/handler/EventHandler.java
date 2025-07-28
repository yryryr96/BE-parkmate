package com.parkmate.parkingreadservice.kafka.handler;

public interface EventHandler<T> {

    boolean supports(Object event);

    void handle(T event);
}
