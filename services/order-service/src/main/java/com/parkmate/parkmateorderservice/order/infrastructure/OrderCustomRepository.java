package com.parkmate.parkmateorderservice.order.infrastructure;

import com.parkmate.parkmateorderservice.common.response.CursorPage;
import com.parkmate.parkmateorderservice.order.domain.Order;
import com.parkmate.parkmateorderservice.order.dto.request.OrdersGetRequestDto;

public interface OrderCustomRepository {

    CursorPage<Order> getOrders(OrdersGetRequestDto ordersGetRequestDto);
}
