package com.parkmate.parkmateorderservice.order.dto.request;

import com.parkmate.parkmateorderservice.order.domain.OrderType;
import com.parkmate.parkmateorderservice.order.domain.PaymentType;
import com.parkmate.parkmateorderservice.order.vo.request.OrdersGetRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrdersGetRequestDto {

    private String userUuid;
    private OrderType orderType;
    private PaymentType paymentType;
    private Integer size;
    private Long cursor;

    @Builder
    private OrdersGetRequestDto(String userUuid,
                                OrderType orderType,
                                PaymentType paymentType,
                                Integer size,
                                Long cursor) {
        this.userUuid = userUuid;
        this.orderType = orderType;
        this.paymentType = paymentType;
        this.size = size;
        this.cursor = cursor;
    }

    public static OrdersGetRequestDto from(String userUuid, OrdersGetRequestVo ordersGetRequestVo) {
        return OrdersGetRequestDto.builder()
                .userUuid(userUuid)
                .orderType(ordersGetRequestVo.getOrderType())
                .paymentType(ordersGetRequestVo.getPaymentType())
                .size(ordersGetRequestVo.getSize())
                .cursor(ordersGetRequestVo.getCursor())
                .build();
    }
}
