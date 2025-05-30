package com.parkmate.reservationservice.reservation.domain;

import com.parkmate.reservationservice.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment( "예약 코드")
    @Column(unique = true, nullable = false)
    private String reservationCode;

    @Comment("사용자 UUID")
    @Column(nullable = false)
    private String userUuid;

    @Comment("주차 공간 ID")
    @Column(nullable = false)
    private Long parkingSpotId;

    @Comment("주차장 UUID")
    @Column(nullable = false)
    private String parkingLotUuid;

    @Comment("차량 번호")
    @Column(nullable = false)
    private String vehicleNumber;

    @Comment("입차 시간")
    @Column(nullable = false)
    private LocalDateTime entryTime;

    @Comment("출차 시간")
    @Column(nullable = false)
    private LocalDateTime exitTime;

    @Comment("예약 금액")
    @Column(nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Comment("예약 상태")
    @Column(nullable = false)
    private ReservationStatus status;

    @Enumerated(EnumType.STRING)
    @Comment("결제 유형")
    @Column(nullable = false)
    private PaymentType paymentType;

    @Builder
    private Reservation(Long id,
                       String reservationCode,
                       String userUuid,
                       Long parkingSpotId,
                       String parkingLotUuid,
                       String vehicleNumber,
                       LocalDateTime entryTime,
                       LocalDateTime exitTime,
                       double amount,
                       ReservationStatus status,
                       PaymentType paymentType) {
        this.id = id;
        this.reservationCode = reservationCode;
        this.userUuid = userUuid;
        this.parkingSpotId = parkingSpotId;
        this.parkingLotUuid = parkingLotUuid;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.amount = amount;
        this.status = status;
        this.paymentType = paymentType;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
    }
}
