package com.parkmate.parkmateorderservice.order.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PreOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("주문 코드")
    @Column(nullable = false, unique = true, length = 36)
    private String orderCode;

    @Comment("구매자 UUID")
    @Column(nullable = false, length = 36)
    private String userUuid;

    @Comment("주차장 UUID")
    @Column(nullable = false, length = 36)
    private String parkingLotUuid;

    @Comment("차량 번호")
    @Column(length = 20)
    private String vehicleNumber;

    @Comment("결제 타입")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Comment("주문 금액")
    @Column(nullable = false)
    private double amount;

    @Comment("입차 시간")
    @Column(nullable = false)
    private LocalDateTime entryTime;

    @Comment("출차 시간")
    @Column(nullable = false)
    private LocalDateTime exitTime;
}
