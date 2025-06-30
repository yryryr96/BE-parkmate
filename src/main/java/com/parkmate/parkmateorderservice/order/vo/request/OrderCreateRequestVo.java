package com.parkmate.parkmateorderservice.order.vo.request;

import com.parkmate.parkmateorderservice.order.domain.OrderType;
import com.parkmate.parkmateorderservice.order.domain.PaymentType;
import lombok.Getter;

@Getter
public class OrderCreateRequestVo {

    private OrderType orderType;
    private String productCode;
    private double amount;
    private PaymentType paymentType;
}
