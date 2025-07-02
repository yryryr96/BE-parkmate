package com.parkmate.reservationservice.reservation.dto.response;

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
    private String parkingSpotName;
    private String parkingLotUuid;
    private String parkingLotName;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private ReservationStatus status;

    @Builder
    private ReservationResponseDto(String reservationCode,
                                   String parkingSpotName,
                                   String parkingLotUuid,
                                   String parkingLotName,
                                   String vehicleNumber,
                                   LocalDateTime entryTime,
                                   LocalDateTime exitTime,
                                   ReservationStatus status) {
        this.reservationCode = reservationCode;
        this.parkingSpotName = parkingSpotName;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingLotName = parkingLotName;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.status = status;
    }

    public static ReservationResponseDto from(Reservation reservation) {
        return ReservationResponseDto.builder()
                .reservationCode(reservation.getReservationCode())
                .parkingSpotName(reservation.getParkingSpotName())
                .parkingLotUuid(reservation.getParkingLotUuid())
                .parkingLotName(reservation.getParkingLotName())
                .vehicleNumber(reservation.getVehicleNumber())
                .entryTime(reservation.getEntryTime())
                .exitTime(reservation.getExitTime())
                .status(reservation.getStatus())
                .build();
    }

    public ReservationResponseVo toVo() {
        return ReservationResponseVo.builder()
                .reservationCode(reservationCode)
                .parkingSpotName(parkingSpotName)
                .parkingLotUuid(parkingLotUuid)
                .parkingLotName(parkingLotName)
                .vehicleNumber(vehicleNumber)
                .entryTime(entryTime)
                .exitTime(exitTime)
                .status(status)
                .build();
    }
}
