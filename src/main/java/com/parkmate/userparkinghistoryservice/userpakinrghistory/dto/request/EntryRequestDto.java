package com.parkmate.userparkinghistoryservice.userpakinrghistory.dto.request;

import com.parkmate.userparkinghistoryservice.kafka.event.EventType;
import com.parkmate.userparkinghistoryservice.kafka.event.HistoryEvent;
import com.parkmate.userparkinghistoryservice.userpakinrghistory.domain.HistoryType;
import com.parkmate.userparkinghistoryservice.userpakinrghistory.vo.request.EntryRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EntryRequestDto {

    private String userUuid;
    private String reservationCode;
    private String parkingLotUuid;
    private String vehicleNumber;

    @Builder
    private EntryRequestDto(String userUuid,
                            String reservationCode,
                            String parkingLotUuid,
                            String vehicleNumber) {
        this.userUuid = userUuid;
        this.reservationCode = reservationCode;
        this.parkingLotUuid = parkingLotUuid;
        this.vehicleNumber = vehicleNumber;
    }

    public static EntryRequestDto of(String userUuid,
                                     EntryRequestVo entryRequestVo) {
        return EntryRequestDto.builder()
                .userUuid(userUuid)
                .reservationCode(entryRequestVo.getReservationCode())
                .parkingLotUuid(entryRequestVo.getParkingLotUuid())
                .vehicleNumber(entryRequestVo.getVehicleNumber())
                .build();
    }

    public HistoryEvent toEvent(String parkingSpotName, EventType eventType) {
        return HistoryEvent.builder()
                .eventType(eventType)
                .userUuid(userUuid)
                .reservationCode(reservationCode)
                .parkingLotUuid(parkingLotUuid)
                .vehicleNumber(vehicleNumber)
                .parkingSpotName(parkingSpotName)
                .type(HistoryType.ENTRY)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
