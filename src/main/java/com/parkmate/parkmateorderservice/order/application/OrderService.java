package com.parkmate.parkmateorderservice.order.application;

import com.parkmate.parkmateorderservice.order.domain.OrderStatus;
import com.parkmate.parkmateorderservice.order.dto.request.OrderCancelRequestDto;
import com.parkmate.parkmateorderservice.order.dto.request.OrderCreateRequestDto;
import com.parkmate.parkmateorderservice.order.dto.response.OrderCreateResponseDto;

public interface OrderService {

    OrderCreateResponseDto create(OrderCreateRequestDto orderCreateRequestDto);

    void cancel(OrderCancelRequestDto orderCancelRequestDto);

    void changeStatus(String orderCode, OrderStatus orderStatus);
}
