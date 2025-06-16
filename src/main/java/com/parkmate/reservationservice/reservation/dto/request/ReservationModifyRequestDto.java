package com.parkmate.reservationservice.reservation.dto.request;

import com.parkmate.reservationservice.reservation.vo.request.ReservationModifyRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationModifyRequestDto {

    private String userUuid;
    private String reservationCode;
    private LocalDateTime newEntryTime;
    private LocalDateTime newExitTime;

    @Builder
    private ReservationModifyRequestDto(String userUuid,
                                        String reservationCode,
                                        LocalDateTime newEntryTime,
                                        LocalDateTime newExitTime) {
        this.userUuid = userUuid;
        this.reservationCode = reservationCode;
        this.newEntryTime = newEntryTime;
        this.newExitTime = newExitTime;
    }

    public static ReservationModifyRequestDto of(String userUuid,
                                                 String reservationCode,
                                                 ReservationModifyRequestVo reservationModifyRequestVo) {
        return ReservationModifyRequestDto.builder()
                .reservationCode(reservationCode)
                .userUuid(userUuid)
                .newEntryTime(reservationModifyRequestVo.getNewEntryTime())
                .newExitTime(reservationModifyRequestVo.getNewExitTime())
                .build();
    }
}
