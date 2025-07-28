package com.parkmate.parkmateorderservice.order.dto.request;

import com.parkmate.parkmateorderservice.order.domain.PaymentType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PreOrderCreateRequestDto {

    private String orderCode;
    private String userUuid;
    private String parkingLotUuid;
    private String vehicleNumber;
    private PaymentType paymentType;
    private long amount;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    @Builder
    private PreOrderCreateRequestDto(String orderCode,
                                     String userUuid,
                                     String parkingLotUuid,
                                     String vehicleNumber,
                                     PaymentType paymentType,
                                     long amount,
                                     LocalDateTime entryTime,
                                     LocalDateTime exitTime) {
        this.orderCode = orderCode;
        this.userUuid = userUuid;
        this.parkingLotUuid = parkingLotUuid;
        this.vehicleNumber = vehicleNumber;
        this.paymentType = paymentType;
        this.amount = amount;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }
}
