package com.parkmate.parkingreadservice.parkingoperation.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "parking_operation_read")
@Getter
@CompoundIndex(def = "{'parkingLotUuid': 1, 'operationDate': 1}", unique = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingLotOperationRead {

    @Id
    private String id;
    private String parkingLotUuid;
    private LocalDateTime operationDate;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int baseIntervalMinutes;
    private int baseFee;
    private int extraIntervalMinutes;
    private int extraFee;
    private double discountRate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private ParkingLotOperationRead(String id,
                                    String parkingLotUuid,
                                    LocalDateTime operationDate,
                                    LocalDateTime startDateTime,
                                    LocalDateTime endDateTime,
                                    int baseIntervalMinutes,
                                    int baseFee,
                                    int extraIntervalMinutes,
                                    int extraFee,
                                    double discountRate,
                                    LocalDateTime createdAt,
                                    LocalDateTime updatedAt) {
        this.id = id;
        this.parkingLotUuid = parkingLotUuid;
        this.operationDate = operationDate;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.baseIntervalMinutes = baseIntervalMinutes;
        this.baseFee = baseFee;
        this.extraIntervalMinutes = extraIntervalMinutes;
        this.extraFee = extraFee;
        this.discountRate = discountRate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
