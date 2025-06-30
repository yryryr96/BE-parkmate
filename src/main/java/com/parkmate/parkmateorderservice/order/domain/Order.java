package com.parkmate.parkmateorderservice.order.domain;

import com.parkmate.parkmateorderservice.common.entity.BaseEntity;
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
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("주문 코드")
    @Column(nullable = false, unique = true, length = 36)
    private String orderCode;

    @Comment("예약 코드")
    @Column(nullable = false, unique = true, length = 36)
    private String reservationCode;

    @Comment("구매자 UUID")
    @Column(nullable = false, length = 36)
    private String userUuid;

    @Comment("주차장 UUID")
    @Column(nullable = false, length = 36)
    private String parkingLotUuid;

    @Comment("주차장 이름")
    @Column(nullable = false, length = 100)
    private String parkingLotName;

    @Comment("주차 공간 이름")
    @Column(nullable = false, length = 50)
    private String parkingSpotName;

    @Comment("차량 번호")
    @Column(nullable = false, length = 20)
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    @Comment("결제 타입")
    @Column(nullable = false)
    private PaymentType paymentType;

    @Comment("주문 금액")
    @Column(nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Comment("주문 상태")
    @Column(nullable = false)
    private OrderStatus status;

    @Comment("입차 시간")
    @Column(nullable = false)
    private LocalDateTime entryTime;

    @Comment("출차 시간")
    @Column(nullable = false)
    private LocalDateTime exitTime;

    @Builder
    private Order(String orderCode,
                  String userUuid,
                  String reservationCode,
                  String parkingLotUuid,
                  String parkingLotName,
                  String parkingSpotName,
                  String vehicleNumber,
                  PaymentType paymentType,
                  double amount,
                  OrderStatus status,
                  LocalDateTime entryTime,
                  LocalDateTime exitTime) {
        this.orderCode = orderCode;
        this.userUuid = userUuid;
        this.reservationCode = reservationCode;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingLotName = parkingLotName;
        this.parkingSpotName = parkingSpotName;
        this.vehicleNumber = vehicleNumber;
        this.paymentType = paymentType;
        this.amount = amount;
        this.status = status;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }
}
