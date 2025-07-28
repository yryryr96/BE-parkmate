package com.parkmate.parkmateorderservice.order.application.handler.payment;

import com.parkmate.parkmateorderservice.kafka.event.order.OrderEvent;
import com.parkmate.parkmateorderservice.kafka.event.order.OrderEventType;
import com.parkmate.parkmateorderservice.kafka.event.payment.PaymentEvent;
import com.parkmate.parkmateorderservice.kafka.event.payment.PaymentEventType;
import com.parkmate.parkmateorderservice.order.application.OrderService;
import com.parkmate.parkmateorderservice.order.application.handler.EventHandler;
import com.parkmate.parkmateorderservice.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentCancelEventHandler implements EventHandler<PaymentEvent> {

    private final OrderService orderService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean supports(Object event) {
        return event instanceof PaymentEvent && ((PaymentEvent) event).getEventType() == PaymentEventType.CANCELLED;
    }

    @Transactional
    @Override
    public void handle(PaymentEvent event) {
        Order order = orderService.cancel(event.getOrderCode());
        eventPublisher.publishEvent(OrderEvent.from(order, OrderEventType.CANCELLED));
    }
}
