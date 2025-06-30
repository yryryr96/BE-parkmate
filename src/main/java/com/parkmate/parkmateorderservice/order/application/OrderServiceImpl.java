package com.parkmate.parkmateorderservice.order.application;

import com.parkmate.parkmateorderservice.order.dto.request.OrderCancelRequestDto;
import com.parkmate.parkmateorderservice.order.dto.request.OrderCreateRequestDto;
import com.parkmate.parkmateorderservice.order.dto.request.OrderUpdateRequestDto;
import com.parkmate.parkmateorderservice.order.infrastructure.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public void preOrder(OrderCreateRequestDto orderCreateRequestDto) {

    }

    @Transactional
    @Override
    public void cancel(OrderCancelRequestDto orderCancelRequestDto) {

    }

    @Transactional
    @Override
    public void update(OrderUpdateRequestDto orderModifyRequestDto) {

    }
}
