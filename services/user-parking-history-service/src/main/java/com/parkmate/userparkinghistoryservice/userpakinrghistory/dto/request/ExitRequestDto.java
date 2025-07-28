package com.parkmate.userparkinghistoryservice.userpakinrghistory.dto.request;

import com.parkmate.userparkinghistoryservice.kafka.event.EventType;
import com.parkmate.userparkinghistoryservice.kafka.event.HistoryEvent;
import com.parkmate.userparkinghistoryservice.userpakinrghistory.domain.HistoryType;
import com.parkmate.userparkinghistoryservice.userpakinrghistory.vo.request.ExitRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ExitRequestDto {

    private String userUuid;
    private String reservationCode;
    private String parkingLotUuid;
    private String vehicleNumber;

    @Builder
    public ExitRequestDto(String userUuid,
                          String reservationCode,
                          String parkingLotUuid,
                          String vehicleNumber) {
        this.userUuid = userUuid;
        this.reservationCode = reservationCode;
        this.parkingLotUuid = parkingLotUuid;
        this.vehicleNumber = vehicleNumber;
    }

    public static ExitRequestDto of(String userUuid, ExitRequestVo exitRequestVo) {
        return ExitRequestDto.builder()
                .userUuid(userUuid)
                .reservationCode(exitRequestVo.getReservationCode())
                .parkingLotUuid(exitRequestVo.getParkingLotUuid())
                .vehicleNumber(exitRequestVo.getVehicleNumber())
                .build();
    }

    public HistoryEvent toEvent(String parkingSpotName,
                                EventType eventType) {
        return HistoryEvent.builder()
                .eventType(eventType)
                .userUuid(userUuid)
                .parkingLotUuid(parkingLotUuid)
                .parkingSpotName(parkingSpotName)
                .reservationCode(reservationCode)
                .vehicleNumber(vehicleNumber)
                .type(HistoryType.EXIT)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
