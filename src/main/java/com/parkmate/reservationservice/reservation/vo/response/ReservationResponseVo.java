package com.parkmate.reservationservice.reservation.vo.response;

import com.parkmate.reservationservice.reservation.domain.PaymentType;
import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationResponseVo {

    private String reservationCode;
    private String userUuid;
    private Long parkingSpotId;
    private String parkingLotUuid;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amount;
    private ReservationStatus reservationStatus;
    private PaymentType paymentType;

    @Builder
    private ReservationResponseVo(String reservationCode,
                                 String userUuid,
                                 Long parkingSpotId,
                                 String parkingLotUuid,
                                 String vehicleNumber,
                                 LocalDateTime entryTime,
                                 LocalDateTime exitTime,
                                 double amount,
                                 ReservationStatus reservationStatus,
                                 PaymentType paymentType) {
        this.reservationCode = reservationCode;
        this.userUuid = userUuid;
        this.parkingSpotId = parkingSpotId;
        this.parkingLotUuid = parkingLotUuid;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.amount = amount;
        this.reservationStatus = reservationStatus;
        this.paymentType = paymentType;
    }
}
