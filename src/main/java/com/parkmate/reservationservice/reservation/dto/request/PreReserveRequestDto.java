package com.parkmate.reservationservice.reservation.dto.request;

import com.parkmate.reservationservice.common.generator.ReservationCodeGenerator;
import com.parkmate.reservationservice.reservation.domain.Reservation;
import com.parkmate.reservationservice.reservation.domain.ReservationStatus;
import com.parkmate.reservationservice.reservation.vo.ParkingSpot;
import com.parkmate.reservationservice.reservation.vo.request.PreReserveRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PreReserveRequestDto {

    private String userUuid;
    private String parkingSpotType;
    private String parkingLotUuid;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    @Builder
    private PreReserveRequestDto(String userUuid,
                                 String parkingSpotType,
                                 String parkingLotUuid,
                                 String vehicleNumber,
                                 LocalDateTime entryTime,
                                 LocalDateTime exitTime) {
        this.userUuid = userUuid;
        this.parkingSpotType = parkingSpotType;
        this.parkingLotUuid = parkingLotUuid;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public static PreReserveRequestDto of(String userUuid,
                                          PreReserveRequestVo preReserveRequestVo) {
        return PreReserveRequestDto.builder()
                .userUuid(userUuid)
                .parkingSpotType(preReserveRequestVo.getParkingSpotType())
                .parkingLotUuid(preReserveRequestVo.getParkingLotUuid())
                .vehicleNumber(preReserveRequestVo.getVehicleNumber())
                .entryTime(preReserveRequestVo.getEntryTime())
                .exitTime(preReserveRequestVo.getExitTime())
                .build();
    }

    public Reservation toEntity(String parkingLotName,
                                ParkingSpot parkingSpot) {

        return Reservation.builder()
                .reservationCode(ReservationCodeGenerator.generate())
                .userUuid(userUuid)
                .parkingLotUuid(parkingLotUuid)
                .parkingLotName(parkingLotName)
                .parkingSpotId(parkingSpot.getId())
                .parkingSpotName(parkingSpot.getName())
                .vehicleNumber(vehicleNumber)
                .entryTime(entryTime)
                .exitTime(exitTime)
                .status(ReservationStatus.WAITING)
                .build();
    }
}
