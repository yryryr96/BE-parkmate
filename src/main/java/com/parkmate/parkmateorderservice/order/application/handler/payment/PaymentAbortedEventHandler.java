package com.parkmate.parkmateorderservice.order.application.handler.payment;

import com.parkmate.parkmateorderservice.kafka.event.payment.PaymentEvent;
import com.parkmate.parkmateorderservice.kafka.event.payment.PaymentEventType;
import com.parkmate.parkmateorderservice.order.application.OrderService;
import com.parkmate.parkmateorderservice.order.application.handler.EventHandler;
import com.parkmate.parkmateorderservice.order.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentAbortedEventHandler implements EventHandler<PaymentEvent> {

    private final OrderService orderService;

    @Override
    public boolean supports(Object event) {
        return event instanceof PaymentEvent && ((PaymentEvent) event).getEventType() == PaymentEventType.ABORTED;
    }

    @Override
    public void handle(PaymentEvent event) {
        orderService.changeStatus(event.getOrderCode(), OrderStatus.PAYMENT_FAILED);
    }
}
