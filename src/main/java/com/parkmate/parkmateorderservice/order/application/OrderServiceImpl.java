package com.parkmate.parkmateorderservice.order.application;

import com.parkmate.parkmateorderservice.common.exception.BaseException;
import com.parkmate.parkmateorderservice.common.response.CursorPage;
import com.parkmate.parkmateorderservice.order.domain.Order;
import com.parkmate.parkmateorderservice.order.domain.OrderStatus;
import com.parkmate.parkmateorderservice.order.dto.request.OrderCreateRequestDto;
import com.parkmate.parkmateorderservice.order.dto.request.OrderGetRequestDto;
import com.parkmate.parkmateorderservice.order.dto.request.OrdersGetRequestDto;
import com.parkmate.parkmateorderservice.order.dto.response.OrderCreateResponseDto;
import com.parkmate.parkmateorderservice.order.dto.response.OrderResponseDto;
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
    public Order changeStatus(String orderCode,
                              OrderStatus orderStatus) {

        Order order = orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new BaseException(RESOURCE_NOT_FOUND));

        order.changeStatus(orderStatus);
        return order;
    }

    @Transactional(readOnly = true)
    @Override
    public OrderResponseDto getOrder(OrderGetRequestDto orderGetRequestDto) {

        Order order = orderRepository.findByOrderCodeAndUserUuid(
                        orderGetRequestDto.getOrderCode(),
                        orderGetRequestDto.getUserUuid()
                )
                .orElseThrow(() -> new BaseException(RESOURCE_NOT_FOUND));

        return OrderResponseDto.from(order);
    }

    @Transactional(readOnly = true)
    @Override
    public CursorPage<OrderResponseDto> getOrders(OrdersGetRequestDto ordersGetRequestDto) {

        CursorPage<Order> orders = orderRepository.getOrders(ordersGetRequestDto);
        return orders.map(OrderResponseDto::from);
    }
}
