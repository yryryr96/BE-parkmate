package com.parkmate.reservationservice.reservation.dto.request;

import com.parkmate.reservationservice.reservation.vo.request.ReservationCancelRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationCancelRequestDto {

    private String reservationCode;
    private String userUuid;
    private String cancelReason;

    @Builder
    private ReservationCancelRequestDto(String reservationCode,
                                        String userUuid,
                                        String cancelReason) {
        this.reservationCode = reservationCode;
        this.userUuid = userUuid;
        this.cancelReason = cancelReason;
    }

    public static ReservationCancelRequestDto of(String userUuid,
                                                 String reservationCode,
                                                 ReservationCancelRequestVo reservationCancelRequestVo) {
        return ReservationCancelRequestDto.builder()
                .reservationCode(reservationCode)
                .userUuid(userUuid)
                .cancelReason(reservationCancelRequestVo.getCancelReason())
                .build();
    }
}
