package com.parkmate.parkmateorderservice.order.dto.response;

import com.parkmate.parkmateorderservice.order.domain.Order;
import com.parkmate.parkmateorderservice.order.domain.PaymentType;
import com.parkmate.parkmateorderservice.order.vo.response.OrderResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderResponseDto {

    private String orderCode;
    private long amount;
    private PaymentType paymentType;
    private LocalDateTime timestamp;

    @Builder
    private OrderResponseDto(String orderCode,
                             long amount,
                             PaymentType paymentType,
                             LocalDateTime timestamp) {
        this.orderCode = orderCode;
        this.amount = amount;
        this.paymentType = paymentType;
        this.timestamp = timestamp;
    }

    public static OrderResponseDto from(Order order) {
        return OrderResponseDto.builder()
                .orderCode(order.getOrderCode())
                .amount(order.getAmount())
                .paymentType(order.getPaymentType())
                .timestamp(order.getCreatedAt())
                .build();
    }

    public OrderResponseVo toVo() {
        return OrderResponseVo.builder()
                .orderCode(orderCode)
                .amount(amount)
                .paymentType(paymentType)
                .timestamp(timestamp)
                .build();
    }
}
