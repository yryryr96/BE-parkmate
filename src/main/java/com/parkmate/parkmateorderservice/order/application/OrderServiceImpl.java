package com.parkmate.parkmateorderservice.order.application;

import com.parkmate.parkmateorderservice.common.exception.BaseException;
import com.parkmate.parkmateorderservice.order.domain.Order;
import com.parkmate.parkmateorderservice.order.domain.OrderStatus;
import com.parkmate.parkmateorderservice.order.dto.request.OrderCreateRequestDto;
import com.parkmate.parkmateorderservice.order.dto.response.OrderCreateResponseDto;
import com.parkmate.parkmateorderservice.order.infrastructure.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.parkmate.parkmateorderservice.common.response.ResponseStatus.RESOURCE_NOT_FOUND;

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
    public Order confirm(String orderCode) {

        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new BaseException(RESOURCE_NOT_FOUND));

        return order.confirm();
    }

    @Transactional
    @Override
    public Order cancel(String orderCode) {

        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new BaseException(RESOURCE_NOT_FOUND));

        return order.cancel();
    }

    @Transactional
    @Override
    public Order changeStatus(String orderCode, OrderStatus orderStatus) {

        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new BaseException(RESOURCE_NOT_FOUND));

        order.changeStatus(orderStatus);
        return order;
    }
}
