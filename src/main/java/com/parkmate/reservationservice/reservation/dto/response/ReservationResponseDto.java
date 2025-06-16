package com.parkmate.reservationservice.reservation.dto.response;

import com.parkmate.reservationservice.reservation.domain.PaymentType;
import com.parkmate.reservationservice.reservation.domain.Reservation;
import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import com.parkmate.reservationservice.reservation.vo.response.ReservationResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationResponseDto {

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
    private ReservationResponseDto(String reservationCode,
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

    public static ReservationResponseDto from(Reservation reservation) {
        return ReservationResponseDto.builder()
                .reservationCode(reservation.getReservationCode())
                .userUuid(reservation.getUserUuid())
                .parkingSpotId(reservation.getParkingSpotId())
                .parkingLotUuid(reservation.getParkingLotUuid())
                .vehicleNumber(reservation.getVehicleNumber())
                .entryTime(reservation.getEntryTime())
                .exitTime(reservation.getExitTime())
                .amount(reservation.getAmount())
                .reservationStatus(reservation.getStatus())
                .paymentType(reservation.getPaymentType())
                .build();
    }

    public ReservationResponseVo toVo() {
        return ReservationResponseVo.builder()
                .reservationCode(reservationCode)
                .userUuid(userUuid)
                .parkingSpotId(parkingSpotId)
                .parkingLotUuid(parkingLotUuid)
                .vehicleNumber(vehicleNumber)
                .entryTime(entryTime)
                .exitTime(exitTime)
                .amount(amount)
                .reservationStatus(reservationStatus)
                .paymentType(paymentType)
                .build();
    }
}
