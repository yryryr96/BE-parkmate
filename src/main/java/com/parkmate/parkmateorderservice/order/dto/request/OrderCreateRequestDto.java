package com.parkmate.parkmateorderservice.order.dto.request;

import com.parkmate.parkmateorderservice.order.domain.Order;
import com.parkmate.parkmateorderservice.order.domain.ParkingSpotType;
import com.parkmate.parkmateorderservice.order.vo.request.OrderCreateRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {

    private String userUuid;
    private String vehicleNumber;
    private double amount;
    private ParkingSpotType parkingSpotType;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    @Builder
    private OrderCreateRequestDto(String userUuid,
                                 String vehicleNumber,
                                 double amount,
                                 ParkingSpotType parkingSpotType,
                                 LocalDateTime entryTime,
                                 LocalDateTime exitTime) {
        this.userUuid = userUuid;
        this.vehicleNumber = vehicleNumber;
        this.amount = amount;
        this.parkingSpotType = parkingSpotType;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public static OrderCreateRequestDto of(String userUuid,
                                           OrderCreateRequestVo orderCreateRequestVo) {
        return OrderCreateRequestDto.builder()
                .userUuid(userUuid)
                .vehicleNumber(orderCreateRequestVo.getVehicleNumber())
                .amount(orderCreateRequestVo.getAmount())
                .parkingSpotType(orderCreateRequestVo.getParkingSpotType())
                .entryTime(orderCreateRequestVo.getEntryTime())
                .exitTime(orderCreateRequestVo.getExitTime())
                .build();
    }

    public Order toEntity() {
        return Order.builder()
                .userUuid(this.userUuid)
                .vehicleNumber(this.vehicleNumber)
                .amount(this.amount)
                .parkingSpotType(this.parkingSpotType)
                .entryTime(this.entryTime)
                .exitTime(this.exitTime)
                .build();
    }
}
