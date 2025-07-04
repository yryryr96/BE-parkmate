package com.parkmate.parkmateorderservice.order.domain;

import com.parkmate.parkmateorderservice.common.entity.BaseEntity;
import com.parkmate.parkmateorderservice.common.exception.BaseException;
import com.parkmate.parkmateorderservice.common.response.ResponseStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

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

    @Enumerated(EnumType.STRING)
    @Comment("주문 타입")
    @Column(nullable = false)
    private OrderType orderType;

    @Comment("예약 코드")
    @Column(nullable = false, unique = true)
    private String productCode;

    @Comment("구매자 UUID")
    @Column(nullable = false)
    private String userUuid;

    @Comment("주문 금액")
    @Column(nullable = false)
    private long amount;

    @Enumerated(EnumType.STRING)
    @Comment("주문 상태")
    @Column(nullable = false)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Comment("결제 타입")
    @Column(nullable = false)
    private PaymentType paymentType;

    @Builder
    private Order(String orderCode, OrderType orderType, String productCode, String userUuid,
                  long amount, OrderStatus status, PaymentType paymentType) {

        this.orderCode = orderCode;
        this.orderType = orderType;
        this.productCode = productCode;
        this.userUuid = userUuid;
        this.amount = amount;
        this.status = status;
        this.paymentType = paymentType;
    }

    public void changeStatus(OrderStatus orderStatus) {
        status = orderStatus;
    }

    public Order confirm() {

        if (this.status != OrderStatus.PENDING && this.status != OrderStatus.PAYMENT_FAILED) {
            throw new BaseException(ResponseStatus.INVALID_ORDER_STATUS, "이미 처리된 주문입니다.");
        }

        this.status = OrderStatus.PAID;
        return this;
    }

    public Order cancel() {
        this.status = OrderStatus.CANCELLED;
        return this;
    }
}
