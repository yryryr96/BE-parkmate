package com.parkmate.parkmateorderservice.order.application.handler;

public interface EventHandler<T> {

    boolean supports(Object event);

    void handle(T event);
}
