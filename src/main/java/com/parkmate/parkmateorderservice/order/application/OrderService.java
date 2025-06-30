package com.parkmate.parkmateorderservice.order.application;

import com.parkmate.parkmateorderservice.order.dto.request.OrderCancelRequestDto;
import com.parkmate.parkmateorderservice.order.dto.request.OrderCreateRequestDto;
import com.parkmate.parkmateorderservice.order.dto.request.OrderUpdateRequestDto;

public interface OrderService {

    void preOrder(PreOrderCreateRequestDto preOrderCreateRequestDto);

    void create(OrderCreateRequestDto orderCreateRequestDto);

    void cancel(OrderCancelRequestDto orderCancelRequestDto);

    void update(OrderUpdateRequestDto orderModifyRequestDto);
}
