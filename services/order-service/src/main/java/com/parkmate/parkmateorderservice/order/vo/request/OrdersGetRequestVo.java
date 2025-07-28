package com.parkmate.parkmateorderservice.order.vo.request;

import com.parkmate.parkmateorderservice.order.domain.OrderType;
import com.parkmate.parkmateorderservice.order.domain.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrdersGetRequestVo {

    private OrderType orderType;
    private PaymentType paymentType;
    private Integer size;
    private Long cursor;
}
