package com.parkmate.parkmateorderservice.order.application;

import com.parkmate.parkmateorderservice.common.exception.BaseException;
import com.parkmate.parkmateorderservice.common.response.ResponseStatus;
import com.parkmate.parkmateorderservice.order.domain.Order;
import com.parkmate.parkmateorderservice.order.domain.OrderStatus;
import com.parkmate.parkmateorderservice.order.dto.request.OrderCancelRequestDto;
import com.parkmate.parkmateorderservice.order.dto.request.OrderCreateRequestDto;
import com.parkmate.parkmateorderservice.order.dto.request.OrderUpdateRequestDto;
import com.parkmate.parkmateorderservice.order.dto.response.OrderCreateResponseDto;
import com.parkmate.parkmateorderservice.order.infrastructure.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.parkmate.parkmateorderservice.common.response.ResponseStatus.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public OrderCreateResponseDto create(OrderCreateRequestDto orderCreateRequestDto) {
        Order order = orderRepository.save(orderCreateRequestDto.toEntity());
        return OrderCreateResponseDto.from(order);
    }

    @Transactional
    @Override
    public void cancel(OrderCancelRequestDto orderCancelRequestDto) {

    }

    @Transactional
    @Override
    public void changeStatus(String orderCode, OrderStatus orderStatus) {

        Order order = orderRepository.findByOrderCode(orderCode).orElseThrow(
                () -> new BaseException(RESOURCE_NOT_FOUND));

        order.changeStatus(orderStatus);
    }
}
