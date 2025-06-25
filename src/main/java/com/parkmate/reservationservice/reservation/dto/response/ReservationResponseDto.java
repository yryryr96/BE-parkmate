package com.parkmate.reservationservice.reservation.dto.response;

import com.parkmate.reservationservice.reservation.domain.PaymentType;
import com.parkmate.reservationservice.reservation.domain.Reservation;
import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import com.parkmate.reservationservice.reservation.infrastructure.client.response.ReservedParkingLotSimpleResponse;
import com.parkmate.reservationservice.reservation.infrastructure.client.response.ReservedParkingSpotResponse;
import com.parkmate.reservationservice.reservation.infrastructure.client.response.ReservedParkingSpotSimpleResponse;
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
    private String parkingLotThumbnailUrl;
    private Long parkingSpotId;
    private String parkingSpotName;
    private String parkingLotUuid;
    private String parkingLotName;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amount;
    private ReservationStatus status;
    private PaymentType paymentType;

    @Builder
    private ReservationResponseDto(String reservationCode,
                                   String userUuid,
                                      String parkingLotThumbnailUrl,
                                   Long parkingSpotId,
                                   String parkingSpotName,
                                   String parkingLotUuid,
                                   String parkingLotName,
                                   String vehicleNumber,
                                   LocalDateTime entryTime,
                                   LocalDateTime exitTime,
                                   double amount,
                                   ReservationStatus status,
                                   PaymentType paymentType) {
        this.reservationCode = reservationCode;
        this.userUuid = userUuid;
        this.parkingLotThumbnailUrl = parkingLotThumbnailUrl;
        this.parkingSpotId = parkingSpotId;
        this.parkingSpotName = parkingSpotName;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingLotName = parkingLotName;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.amount = amount;
        this.status = status;
        this.paymentType = paymentType;
    }

    public static ReservationResponseDto from(Reservation reservation, ReservedParkingSpotResponse reservedParkingSpotResponse) {
        return ReservationResponseDto.builder()
                .reservationCode(reservation.getReservationCode())
                .userUuid(reservation.getUserUuid())
                .parkingLotThumbnailUrl(reservedParkingSpotResponse.getThumbnailUrl())
                .parkingSpotId(reservation.getParkingSpotId())
                .parkingSpotName(reservedParkingSpotResponse.getParkingSpotName())
                .parkingLotUuid(reservation.getParkingLotUuid())
                .parkingLotName(reservedParkingSpotResponse.getParkingLotName())
                .vehicleNumber(reservation.getVehicleNumber())
                .entryTime(reservation.getEntryTime())
                .exitTime(reservation.getExitTime())
                .amount(reservation.getAmount())
                .status(reservation.getStatus())
                .paymentType(reservation.getPaymentType())
                .build();
    }

    public static ReservationResponseDto from(Reservation reservation,
                              ReservedParkingLotSimpleResponse parkingLotInfo,
                              ReservedParkingSpotSimpleResponse spotInfo) {

        return ReservationResponseDto.builder()
                .reservationCode(reservation.getReservationCode())
                .userUuid(reservation.getUserUuid())
                .parkingLotThumbnailUrl(parkingLotInfo.getThumbnailUrl())
                .parkingSpotId(reservation.getParkingSpotId())
                .parkingSpotName(spotInfo.getParkingSpotName())
                .parkingLotUuid(reservation.getParkingLotUuid())
                .parkingLotName(parkingLotInfo.getParkingLotName())
                .vehicleNumber(reservation.getVehicleNumber())
                .entryTime(reservation.getEntryTime())
                .exitTime(reservation.getExitTime())
                .amount(reservation.getAmount())
                .status(reservation.getStatus())
                .paymentType(reservation.getPaymentType())
                .build();
    }

    public ReservationResponseVo toVo() {
        return ReservationResponseVo.builder()
                .reservationCode(reservationCode)
                .userUuid(userUuid)
                .parkingLotThumbnailUrl(parkingLotThumbnailUrl)
                .parkingSpotId(parkingSpotId)
                .parkingSpotName(parkingSpotName)
                .parkingLotUuid(parkingLotUuid)
                .parkingLotName(parkingLotName)
                .vehicleNumber(vehicleNumber)
                .entryTime(entryTime)
                .exitTime(exitTime)
                .amount(amount)
                .status(status)
                .paymentType(paymentType)
                .build();
    }
}
