package com.parkmate.parkmateorderservice.order.application.handler;

public interface EventHandler<T> {

    boolean supports(T event);

    void handle(T event);
}
