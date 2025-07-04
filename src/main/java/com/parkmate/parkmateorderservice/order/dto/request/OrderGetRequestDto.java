package com.parkmate.parkmateorderservice.order.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderGetRequestDto {

    private String userUuid;
    private String orderCode;

    @Builder
    private OrderGetRequestDto(String userUuid, String orderCode) {
        this.userUuid = userUuid;
        this.orderCode = orderCode;
    }

    public static OrderGetRequestDto of(String userUuid, String orderCode) {
        return OrderGetRequestDto.builder()
                .userUuid(userUuid)
                .orderCode(orderCode)
                .build();
    }
}
