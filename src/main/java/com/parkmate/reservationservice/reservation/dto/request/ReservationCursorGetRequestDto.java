package com.parkmate.reservationservice.reservation.dto.request;

import com.parkmate.reservationservice.reservation.vo.request.ReservationCursorGetRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationCursorGetRequestDto {

    private String userUuid;
    private Integer page;
    private Integer size;
    private Long cursor;

    @Builder
    private ReservationCursorGetRequestDto(String userUuid, Integer page, Integer size, Long cursor) {
        this.userUuid = userUuid;
        this.page = page;
        this.size = size;
        this.cursor = cursor;
    }

    public static ReservationCursorGetRequestDto of(String userUuid,
                                                    ReservationCursorGetRequestVo reservationCursorGetRequestVo) {
        return ReservationCursorGetRequestDto.builder()
                .userUuid(userUuid)
                .page(reservationCursorGetRequestVo.getPage())
                .size(reservationCursorGetRequestVo.getSize())
                .cursor(reservationCursorGetRequestVo.getCursor())
                .build();
    }
}
