package com.parkmate.parkmateorderservice.order.application;

import com.parkmate.parkmateorderservice.order.domain.Order;
import com.parkmate.parkmateorderservice.order.domain.OrderStatus;
import com.parkmate.parkmateorderservice.order.dto.request.OrderCreateRequestDto;
import com.parkmate.parkmateorderservice.order.dto.response.OrderCreateResponseDto;

public interface OrderService {

    OrderCreateResponseDto create(OrderCreateRequestDto orderCreateRequestDto);

    Order confirm(String orderCode);

    Order cancel(String orderCode);

    Order changeStatus(String orderCode, OrderStatus orderStatus);
}
