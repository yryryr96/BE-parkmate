package com.parkmate.reservationservice.reservation.infrastructure.repository;

import com.parkmate.reservationservice.common.response.CursorPage;
import com.parkmate.reservationservice.reservation.domain.QReservation;
import com.parkmate.reservationservice.reservation.domain.Reservation;
import com.parkmate.reservationservice.reservation.dto.request.ReservationCursorGetRequestDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.parkmate.reservationservice.reservation.domain.QReservation.*;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    @Override
    public CursorPage<Reservation> getReservations(ReservationCursorGetRequestDto reservationCursorGetRequestDto) {

        int pageSize = reservationCursorGetRequestDto.getSize() == null ? DEFAULT_PAGE_SIZE : reservationCursorGetRequestDto.getSize();
        int offset = 0;

        Long cursor = reservationCursorGetRequestDto.getCursor();
        BooleanBuilder builder = new BooleanBuilder();
        if (cursor != null) {
            builder.and(reservation.id.loe(cursor));
        } else {
            int currentPage = reservationCursorGetRequestDto.getPage() == null ? DEFAULT_PAGE_NUMBER : reservationCursorGetRequestDto.getPage();
            offset = currentPage == 0 ? 0 : currentPage * pageSize;
        }

        JPAQuery<Reservation> query = queryFactory.selectFrom(reservation)
                .where(
                        userUuidEq(reservationCursorGetRequestDto.getUserUuid()),
                        builder
                )
                .offset(offset)
                .limit(pageSize + 1)
                .orderBy(reservation.id.desc());

        List<Reservation> content = query.fetch();

        Long nextCursor = null;
        boolean hasNext = false;

        if (content.size() > pageSize) {
            nextCursor = content.get(pageSize).getId();
            content.remove(pageSize);
            hasNext = true;
        }

        return CursorPage.<Reservation>builder()
                .content(content)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }

    private BooleanExpression userUuidEq(String userUuid) {
        return userUuid != null ? reservation.userUuid.eq(userUuid) : null;
    }
}
