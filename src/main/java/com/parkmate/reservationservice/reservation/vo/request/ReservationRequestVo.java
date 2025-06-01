package com.parkmate.reservationservice.reservation.vo.request;

import com.parkmate.reservationservice.reservation.domain.PaymentType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationRequestVo {

    private String userUuid;
    private Long parkingSpotId;
    private String parkingLotUuid;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amount;
    private PaymentType paymentType;
}
