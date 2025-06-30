package com.parkmate.parkmateorderservice.order.vo.request;

import com.parkmate.parkmateorderservice.order.domain.ParkingSpotType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderCreateRequestVo {

    private String vehicleNumber;
    private double amount;
    private ParkingSpotType parkingSpotType;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
}
