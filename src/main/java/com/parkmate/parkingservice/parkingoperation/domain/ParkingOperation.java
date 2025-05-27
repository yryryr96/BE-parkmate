package com.parkmate.parkingservice.parkingoperation.domain;

import com.parkmate.parkingservice.common.entity.BaseEntity;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "parking_operations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingOperation extends BaseEntity {

    @Id
    private String parkingOperationUuid;

    @Comment( "주차장 UUID")
    @Column(nullable = false)
    private String parkingLotUuid;

    @Comment("운영 날짜")
    @Column(nullable = false)
    private LocalDate operationDate;

    @Comment("유효 시작 시간")
    @Column(nullable = false)
    private LocalDateTime validStartTime;

    @Comment("유효 종료 시간")
    @Column(nullable = false)
    private LocalDateTime validEndTime;

    @Comment("기본 시간 간격 (분)")
    @Column(nullable = false)
    private int baseIntervalMinutes;

    @Comment("기본 요금 (원)")
    @Column(nullable = false)
    private int baseFee;

    @Comment("추가 시간 간격 (분)")
    @Column(nullable = false)
    private int extraIntervalMinutes;

    @Comment("추가 요금 (원)")
    @Column(nullable = false)
    private int extraFee;

    @Comment("할인율")
    @Column(nullable = false)
    private double discountRate;

    @Builder
    private ParkingOperation(String parkingOperationUuid,
                             String parkingLotUuid,
                             LocalDate operationDate,
                             LocalDateTime validStartTime,
                             LocalDateTime validEndTime,
                             int baseIntervalMinutes,
                             int baseFee,
                             int extraIntervalMinutes,
                             int extraFee,
                             double discountRate) {
        this.parkingOperationUuid = parkingOperationUuid;
        this.parkingLotUuid = parkingLotUuid;
        this.operationDate = operationDate;
        this.validStartTime = validStartTime;
        this.validEndTime = validEndTime;
        this.baseIntervalMinutes = baseIntervalMinutes;
        this.baseFee = baseFee;
        this.extraIntervalMinutes = extraIntervalMinutes;
        this.extraFee = extraFee;
        this.discountRate = discountRate;
    }
}
