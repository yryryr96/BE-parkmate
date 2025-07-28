package com.parkmate.parkmateorderservice.order.dto.request;

import com.parkmate.parkmateorderservice.common.generator.OrderCodeGenerator;
import com.parkmate.parkmateorderservice.order.domain.Order;
import com.parkmate.parkmateorderservice.order.domain.OrderStatus;
import com.parkmate.parkmateorderservice.order.domain.OrderType;
import com.parkmate.parkmateorderservice.order.domain.PaymentType;
import com.parkmate.parkmateorderservice.order.vo.request.OrderCreateRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {

    private String userUuid;
    private OrderType orderType;
    private String productCode;
    private long amount;
    private PaymentType paymentType;

    @Builder
    private OrderCreateRequestDto(String userUuid, OrderType orderType, String productCode, long amount,
                                  PaymentType paymentType) {
        this.userUuid = userUuid;
        this.orderType = orderType;
        this.productCode = productCode;
        this.amount = amount;
        this.paymentType = paymentType;
    }

    public static OrderCreateRequestDto of(String userUuid, OrderCreateRequestVo orderCreateRequestVo) {
        return OrderCreateRequestDto.builder()
                .userUuid(userUuid)
                .orderType(orderCreateRequestVo.getOrderType())
                .productCode(orderCreateRequestVo.getProductCode())
                .amount(orderCreateRequestVo.getAmount())
                .paymentType(orderCreateRequestVo.getPaymentType())
                .build();
    }

    public Order toEntity() {
        return Order.builder()
                .orderCode(OrderCodeGenerator.generate())
                .orderType(orderType)
                .productCode(productCode)
                .userUuid(userUuid)
                .amount(amount)
                .paymentType(paymentType)
                .status(OrderStatus.PENDING)
                .build();
    }
}
