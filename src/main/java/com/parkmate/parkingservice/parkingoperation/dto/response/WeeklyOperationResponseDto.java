package com.parkmate.parkingservice.parkingoperation.dto.response;

import com.parkmate.parkingservice.parkingoperation.vo.request.WeeklyOperationResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class WeeklyOperationResponseDto {

    private String dayOfWeek;
    private int dayOfMonth;
    private LocalTime startTime;
    private LocalTime endTime;

    @Builder
    private WeeklyOperationResponseDto(String dayOfWeek,
                                       int dayOfMonth,
                                       LocalTime startTime,
                                       LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static WeeklyOperationResponseDto of(String dayOfWeek,
                                                int dayOfMonth) {
        return WeeklyOperationResponseDto.builder()
                .dayOfWeek(dayOfWeek)
                .dayOfMonth(dayOfMonth)
                .build();
    }

    public static WeeklyOperationResponseDto of(String dayOfWeek,
                                                int dayOfMonth,
                                                LocalTime startTime,
                                                LocalTime endTime) {
        return WeeklyOperationResponseDto.builder()
                .dayOfWeek(dayOfWeek)
                .dayOfMonth(dayOfMonth)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    public WeeklyOperationResponseVo toVo() {
        return WeeklyOperationResponseVo.builder()
                .dayOfWeek(dayOfWeek)
                .dayOfMonth(dayOfMonth)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
