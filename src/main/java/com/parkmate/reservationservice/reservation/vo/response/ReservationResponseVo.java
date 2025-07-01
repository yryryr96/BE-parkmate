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
    private String userUuid;
    private Long parkingSpotId;
    private String parkingSpotName;
    private String parkingLotUuid;
    private String parkingLotName;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amount;
    private ReservationStatus status;

    @Builder
    private ReservationResponseVo(String reservationCode,
                                  String userUuid,
                                  Long parkingSpotId,
                                  String parkingSpotName,
                                  String parkingLotUuid,
                                  String parkingLotName,
                                  String vehicleNumber,
                                  LocalDateTime entryTime,
                                  LocalDateTime exitTime,
                                  double amount,
                                  ReservationStatus status) {
        this.reservationCode = reservationCode;
        this.userUuid = userUuid;
        this.parkingSpotId = parkingSpotId;
        this.parkingSpotName = parkingSpotName;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingLotName = parkingLotName;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.amount = amount;
        this.status = status;
    }
}
