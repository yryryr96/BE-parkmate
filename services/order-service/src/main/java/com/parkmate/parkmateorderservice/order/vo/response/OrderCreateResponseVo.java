package com.parkmate.parkmateorderservice.order.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateResponseVo {

    private String orderCode;
    private long amount;

    @Builder
    private OrderCreateResponseVo(String orderCode, long amount) {
        this.orderCode = orderCode;
        this.amount = amount;
    }
}
