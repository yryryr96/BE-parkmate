package com.parkmate.reservationservice.reservation.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationGetRequestDto {

    private String reservationCode;
    private String userUuid;

    @Builder
    private ReservationGetRequestDto(String reservationCode,
                                     String userUuid) {
        this.reservationCode = reservationCode;
        this.userUuid = userUuid;
    }

    public static ReservationGetRequestDto of(String userUuid,
                                              String reservationCode) {
        return ReservationGetRequestDto.builder()
                .reservationCode(reservationCode)
                .userUuid(userUuid)
                .build();
    }
}
