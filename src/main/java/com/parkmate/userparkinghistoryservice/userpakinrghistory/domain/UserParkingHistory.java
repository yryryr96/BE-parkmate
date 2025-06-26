package com.parkmate.userparkinghistoryservice.userpakinrghistory.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "user_parking_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserParkingHistory {

    @Id
    private String id;

    private String userUuid;
    private String parkingLotUuid;
    private String parkingSpotName;
    private String reservationCode;
    private String vehicleNumber;
    private HistoryType type;

    @CreatedDate
    private LocalDateTime timestamp;

    @Builder
    private UserParkingHistory(String id,
                               String userUuid,
                               String parkingLotUuid,
                               String parkingSpotName,
                               String reservationCode,
                               String vehicleNumber,
                               HistoryType type,
                               LocalDateTime timestamp) {
        this.id = id;
        this.userUuid = userUuid;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingSpotName = parkingSpotName;
        this.reservationCode = reservationCode;
        this.vehicleNumber = vehicleNumber;
        this.type = type;
        this.timestamp = timestamp;
    }
}
