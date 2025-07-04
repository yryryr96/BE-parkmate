package com.parkmate.parkmateorderservice.order.infrastructure;

import com.parkmate.parkmateorderservice.common.response.CursorPage;
import com.parkmate.parkmateorderservice.order.domain.Order;
import com.parkmate.parkmateorderservice.order.domain.OrderType;
import com.parkmate.parkmateorderservice.order.domain.PaymentType;
import com.parkmate.parkmateorderservice.order.dto.request.OrdersGetRequestDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.parkmate.parkmateorderservice.order.domain.QOrder.order;

@Repository
@RequiredArgsConstructor
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final JPAQueryFactory queryFactory;

    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    public CursorPage<Order> getOrders(OrdersGetRequestDto ordersGetRequestDto) {

        int size = ordersGetRequestDto.getSize() != null ? ordersGetRequestDto.getSize() : DEFAULT_PAGE_SIZE;

        BooleanBuilder builder = new BooleanBuilder();
        Long cursor = ordersGetRequestDto.getCursor();

        if (cursor != null) {
            builder.and(order.id.loe(cursor));
        }

        List<Order> content = queryFactory.selectFrom(order)
                .where(
                        order.userUuid.eq(ordersGetRequestDto.getUserUuid()),
                        eqOrderType(ordersGetRequestDto.getOrderType()),
                        eqPaymentType(ordersGetRequestDto.getPaymentType()),
                        builder
                )
                .limit(size + 1)
                .orderBy(order.id.desc())
                .fetch();

        boolean hasNext = content.size() > size;
        Long nextCursor = null;

        if (hasNext) {
            nextCursor = content.get(size - 1).getId();
            content = content.subList(0, size);
        }

        return CursorPage.<Order>builder()
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .content(content)
                .build();
    }

    private BooleanExpression eqPaymentType(PaymentType paymentType) {
        return paymentType == null ? null : order.paymentType.eq(paymentType);
    }

    private BooleanExpression eqOrderType(OrderType orderType) {
        return orderType == null ? null : order.orderType.eq(orderType);
    }
}
