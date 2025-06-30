package com.parkmate.parkmateorderservice.order.infrastructure;

import com.parkmate.parkmateorderservice.order.domain.Order;
import com.parkmate.parkmateorderservice.order.dto.request.OrderCancelRequestDto;
import com.parkmate.parkmateorderservice.order.dto.request.OrderUpdateRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    void cancel(OrderCancelRequestDto orderCancelRequestDto);

    void update(OrderUpdateRequestDto orderModifyRequestDto);
}
