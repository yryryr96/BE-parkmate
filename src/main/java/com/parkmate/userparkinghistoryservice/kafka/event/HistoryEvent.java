package com.parkmate.userparkinghistoryservice.kafka.event;

import com.parkmate.userparkinghistoryservice.userpakinrghistory.domain.HistoryType;
import com.parkmate.userparkinghistoryservice.userpakinrghistory.domain.UserParkingHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HistoryEvent {

    private EventType eventType;
    private String userUuid;
    private String parkingLotUuid;
    private String parkingSpotName;
    private String reservationCode;
    private String vehicleNumber;
    private HistoryType type;
    private LocalDateTime timestamp;

    @Builder
    private HistoryEvent(EventType eventType,
                        String userUuid,
                        String parkingLotUuid,
                        String parkingSpotName,
                        String reservationCode,
                        String vehicleNumber,
                        HistoryType type,
                        LocalDateTime timestamp) {
        this.eventType = eventType;
        this.userUuid = userUuid;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingSpotName = parkingSpotName;
        this.reservationCode = reservationCode;
        this.vehicleNumber = vehicleNumber;
        this.type = type;
        this.timestamp = timestamp;
    }

    public UserParkingHistory toEntity() {
        return UserParkingHistory.builder()
                .userUuid(userUuid)
                .parkingLotUuid(parkingLotUuid)
                .parkingSpotName(parkingSpotName)
                .reservationCode(reservationCode)
                .vehicleNumber(vehicleNumber)
                .type(type)
                .timestamp(timestamp)
                .build();
    }
}
