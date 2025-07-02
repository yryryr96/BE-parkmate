package com.parkmate.reservationservice.reservation.domain;

import com.parkmate.reservationservice.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("예약 코드")
    @Column(unique = true, nullable = false)
    private String reservationCode;

    @Comment("사용자 UUID")
    @Column(nullable = false)
    private String userUuid;

    @Comment("주차 공간 ID")
    @Column(nullable = false)
    private Long parkingSpotId;

    @Comment("주차 공간 이름")
    @Column(nullable = false)
    private String parkingSpotName;

    @Comment("주차장 UUID")
    @Column(nullable = false)
    private String parkingLotUuid;

    @Comment("주차장 이름")
    @Column(nullable = false)
    private String parkingLotName;

    @Comment("차량 번호")
    private String vehicleNumber;

    @Comment("입차 시간")
    @Column(nullable = false)
    private LocalDateTime entryTime;

    @Comment("출차 시간")
    @Column(nullable = false)
    private LocalDateTime exitTime;

    @Comment("예약 금액")
    private long amount;

    @Enumerated(EnumType.STRING)
    @Comment("예약 상태")
    @Column(nullable = false)
    private ReservationStatus status;

    @Builder
    private Reservation(String reservationCode,
                        String userUuid,
                        Long parkingSpotId,
                        String parkingSpotName,
                        String parkingLotUuid,
                        String parkingLotName,
                        String vehicleNumber,
                        LocalDateTime entryTime,
                        LocalDateTime exitTime,
                        long amount,
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

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
    }

    public void modify(LocalDateTime newEntryTime,
                       LocalDateTime newExitTime) {
        this.entryTime = newEntryTime;
        this.exitTime = newExitTime;
    }

    public void changeStatus(ReservationStatus status) {
        this.status = status;
    }
}
