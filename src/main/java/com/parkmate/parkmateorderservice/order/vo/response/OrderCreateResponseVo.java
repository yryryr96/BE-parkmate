package com.parkmate.parkmateorderservice.order.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateResponseVo {

    private String orderCode;
    private double amount;

    @Builder
    private OrderCreateResponseVo(String orderCode, double amount) {
        this.orderCode = orderCode;
        this.amount = amount;
    }
}
