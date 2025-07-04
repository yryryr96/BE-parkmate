package com.parkmate.parkmateorderservice.order.vo.response;

import com.parkmate.parkmateorderservice.order.domain.PaymentType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderResponseVo {

    private String orderCode;
    private long amount;
    private PaymentType paymentType;
    private LocalDateTime timestamp;

    @Builder
    private OrderResponseVo(String orderCode,
                            long amount,
                            PaymentType paymentType,
                            LocalDateTime timestamp) {
        this.orderCode = orderCode;
        this.amount = amount;
        this.paymentType = paymentType;
        this.timestamp = timestamp;
    }
}
