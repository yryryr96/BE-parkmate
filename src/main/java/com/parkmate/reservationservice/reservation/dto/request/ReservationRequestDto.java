package com.parkmate.reservationservice.reservation.dto.request;

import com.parkmate.reservationservice.common.generator.ReservationCodeGenerator;
import com.parkmate.reservationservice.reservation.domain.PaymentType;
import com.parkmate.reservationservice.reservation.domain.Reservation;
import com.parkmate.reservationservice.reservation.vo.request.ReservationRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationRequestDto {

    private String userUuid;
    private Long parkingSpotId;
    private String parkingLotUuid;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amount;
    private PaymentType paymentType;

    @Builder
    private ReservationRequestDto(String userUuid,
                                 Long parkingSpotId,
                                 String parkingLotUuid,
                                 String vehicleNumber,
                                 LocalDateTime entryTime,
                                 LocalDateTime exitTime,
                                 double amount,
                                 PaymentType paymentType) {
        this.userUuid = userUuid;
        this.parkingSpotId = parkingSpotId;
        this.parkingLotUuid = parkingLotUuid;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.amount = amount;
        this.paymentType = paymentType;
    }

    public static ReservationRequestDto from(ReservationRequestVo reservationRequestVo) {
        return ReservationRequestDto.builder()
                .userUuid(reservationRequestVo.getUserUuid())
                .parkingSpotId(reservationRequestVo.getParkingSpotId())
                .parkingLotUuid(reservationRequestVo.getParkingLotUuid())
                .vehicleNumber(reservationRequestVo.getVehicleNumber())
                .entryTime(reservationRequestVo.getEntryTime())
                .exitTime(reservationRequestVo.getExitTime())
                .amount(reservationRequestVo.getAmount())
                .paymentType(reservationRequestVo.getPaymentType())
                .build();
    }

    public Reservation toEntity() {
        return Reservation.builder()
                .reservationCode(ReservationCodeGenerator.generate())
                .userUuid(this.userUuid)
                .parkingSpotId(this.parkingSpotId)
                .parkingLotUuid(this.parkingLotUuid)
                .vehicleNumber(this.vehicleNumber)
                .entryTime(this.entryTime)
                .exitTime(this.exitTime)
                .amount(this.amount)
                .paymentType(this.paymentType)
                .build();
    }
}
