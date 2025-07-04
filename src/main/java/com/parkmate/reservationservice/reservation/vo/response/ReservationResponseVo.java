package com.parkmate.reservationservice.reservation.vo.response;

import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationResponseVo {

    private String reservationCode;
    private String parkingSpotName;
    private String parkingLotUuid;
    private String parkingLotName;
    private String vehicleNumber;
    private long amount;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private ReservationStatus status;

    @Builder
    private ReservationResponseVo(String reservationCode,
                                  String parkingSpotName,
                                  String parkingLotUuid,
                                  String parkingLotName,
                                  String vehicleNumber,
                                  long amount,
                                  LocalDateTime entryTime,
                                  LocalDateTime exitTime,
                                  ReservationStatus status) {
        this.reservationCode = reservationCode;
        this.parkingSpotName = parkingSpotName;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingLotName = parkingLotName;
        this.vehicleNumber = vehicleNumber;
        this.amount = amount;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.status = status;
    }
}
