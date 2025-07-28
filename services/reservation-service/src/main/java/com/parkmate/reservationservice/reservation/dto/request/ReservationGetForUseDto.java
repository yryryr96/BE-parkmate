package com.parkmate.reservationservice.reservation.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ReservationGetForUseDto {

    private String userUuid;
    private String reservationCode;
    private String parkingLotUuid;

    @Builder
    public ReservationGetForUseDto(String userUuid,
                                   String reservationCode,
                                   String parkingLotUuid) {
        this.userUuid = userUuid;
        this.reservationCode = reservationCode;
        this.parkingLotUuid = parkingLotUuid;
    }

    public static ReservationGetForUseDto of(String userUuid,
                                             String reservationCode,
                                             String parkingLotUuid) {
        return ReservationGetForUseDto.builder()
                .userUuid(userUuid)
                .reservationCode(reservationCode)
                .parkingLotUuid(parkingLotUuid)
                .build();
    }
}
