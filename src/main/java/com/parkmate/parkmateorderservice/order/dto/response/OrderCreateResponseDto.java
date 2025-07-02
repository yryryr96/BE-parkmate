package com.parkmate.parkmateorderservice.order.dto.response;

import com.parkmate.parkmateorderservice.order.domain.Order;
import com.parkmate.parkmateorderservice.order.vo.response.OrderCreateResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateResponseDto {

    private String orderCode;
    private long amount;

    @Builder
    private OrderCreateResponseDto(String orderCode, long amount) {
        this.orderCode = orderCode;
        this.amount = amount;
    }

    public static OrderCreateResponseDto from(Order order) {
        return OrderCreateResponseDto.builder()
                .orderCode(order.getOrderCode())
                .amount(order.getAmount())
                .build();
    }

    public OrderCreateResponseVo toVo() {
        return OrderCreateResponseVo.builder()
                .orderCode(orderCode)
                .amount(amount)
                .build();
    }
}
