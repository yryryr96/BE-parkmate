package com.parkmate.parkmateorderservice.order.application.handler.payment;

import com.parkmate.parkmateorderservice.kafka.event.payment.PaymentEvent;
import com.parkmate.parkmateorderservice.kafka.event.payment.PaymentEventType;
import com.parkmate.parkmateorderservice.order.application.OrderService;
import com.parkmate.parkmateorderservice.order.application.handler.EventHandler;
import com.parkmate.parkmateorderservice.order.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PaymentCancelEventHandler implements EventHandler<PaymentEvent> {

    private final OrderService orderService;

    @Override
    public boolean supports(PaymentEvent event) {
        return event.getEventType() == PaymentEventType.CANCELLED;
    }

    @Override
    public void handle(PaymentEvent event) {
        orderService.changeStatus(event.getOrderCode(), OrderStatus.CANCELLED);
    }
}
