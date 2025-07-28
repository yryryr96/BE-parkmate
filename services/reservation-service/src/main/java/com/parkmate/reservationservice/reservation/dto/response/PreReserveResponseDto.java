package com.parkmate.reservationservice.reservation.dto.response;

import com.parkmate.reservationservice.reservation.domain.Reservation;
import com.parkmate.reservationservice.reservation.vo.response.PreReserveResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PreReserveResponseDto {

    private String reservationCode;
    private String parkingSpotType;
    private String parkingLotUuid;
    private String vehicleNumber;
    private long amount;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    @Builder
    private PreReserveResponseDto(String reservationCode,
                                  String parkingSpotType,
                                  String parkingLotUuid,
                                  String vehicleNumber,
                                  long amount,
                                  LocalDateTime entryTime,
                                  LocalDateTime exitTime) {
        this.reservationCode = reservationCode;
        this.parkingSpotType = parkingSpotType;
        this.parkingLotUuid = parkingLotUuid;
        this.vehicleNumber = vehicleNumber;
        this.amount = amount;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public static PreReserveResponseDto from(Reservation reservation) {
        return PreReserveResponseDto.builder()
                .reservationCode(reservation.getReservationCode())
                .amount(reservation.getAmount())
                .build();
    }

    public PreReserveResponseVo toVo() {
        return PreReserveResponseVo.builder()
                .reservationCode(reservationCode)
                .amount(amount)
                .build();
    }
}
