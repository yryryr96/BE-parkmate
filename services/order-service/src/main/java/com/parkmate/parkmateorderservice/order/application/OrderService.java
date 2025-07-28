package com.parkmate.parkmateorderservice.order.application;

import com.parkmate.parkmateorderservice.common.response.CursorPage;
import com.parkmate.parkmateorderservice.order.domain.Order;
import com.parkmate.parkmateorderservice.order.domain.OrderStatus;
import com.parkmate.parkmateorderservice.order.dto.request.OrderCreateRequestDto;
import com.parkmate.parkmateorderservice.order.dto.request.OrderGetRequestDto;
import com.parkmate.parkmateorderservice.order.dto.request.OrdersGetRequestDto;
import com.parkmate.parkmateorderservice.order.dto.response.OrderCreateResponseDto;
import com.parkmate.parkmateorderservice.order.dto.response.OrderResponseDto;

public interface OrderService {

    OrderCreateResponseDto create(OrderCreateRequestDto orderCreateRequestDto);

    Order confirm(String orderCode);

    Order cancel(String orderCode);

    Order changeStatus(String orderCode, OrderStatus orderStatus);

    OrderResponseDto getOrder(OrderGetRequestDto orderGetRequestDto);

    CursorPage<OrderResponseDto> getOrders(OrdersGetRequestDto ordersGetRequestDto);

    OrderResponseDto getOrderByProductCode(String userUuid,
                                           String productCode);
}
