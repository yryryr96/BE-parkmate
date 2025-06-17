package com.parkmate.reservationservice.reservation.dto.request;

import com.parkmate.reservationservice.common.generator.ReservationCodeGenerator;
import com.parkmate.reservationservice.reservation.domain.PaymentType;
import com.parkmate.reservationservice.reservation.domain.Reservation;
import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import com.parkmate.reservationservice.reservation.vo.ParkingSpot;
import com.parkmate.reservationservice.reservation.vo.request.ReservationCreateRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationCreateRequestDto {

    private String userUuid;
    private String parkingSpotType;
    private String parkingLotUuid;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amount;
    private PaymentType paymentType;

    @Builder
    private ReservationCreateRequestDto(String userUuid,
                                        String parkingSpotType,
                                        String parkingLotUuid,
                                        String vehicleNumber,
                                        LocalDateTime entryTime,
                                        LocalDateTime exitTime,
                                        double amount,
                                        PaymentType paymentType) {
        this.userUuid = userUuid;
        this.parkingSpotType = parkingSpotType;
        this.parkingLotUuid = parkingLotUuid;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.amount = amount;
        this.paymentType = paymentType;
    }

    public static ReservationCreateRequestDto of(String userUuid,
                                                 ReservationCreateRequestVo reservationCreateRequestVo) {
        return ReservationCreateRequestDto.builder()
                .userUuid(userUuid)
                .parkingLotUuid(reservationCreateRequestVo.getParkingLotUuid())
                .parkingSpotType(reservationCreateRequestVo.getParkingSpotType())
                .vehicleNumber(reservationCreateRequestVo.getVehicleNumber())
                .entryTime(reservationCreateRequestVo.getEntryTime())
                .exitTime(reservationCreateRequestVo.getExitTime())
                .amount(reservationCreateRequestVo.getAmount())
                .paymentType(reservationCreateRequestVo.getPaymentType())
                .build();
    }

    public Reservation toEntity(ParkingSpot parkingSpot) {
        return Reservation.builder()
                .reservationCode(ReservationCodeGenerator.generate())
                .userUuid(userUuid)
                .parkingLotUuid(parkingLotUuid)
                .parkingSpotId(parkingSpot.getId())
                .vehicleNumber(vehicleNumber)
                .entryTime(entryTime)
                .exitTime(exitTime)
                .amount(amount)
                .paymentType(paymentType)
                .status(ReservationStatus.CONFIRMED)
                .build();
    }
}
