package com.parkmate.parkingservice.parkingoperation.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class WeeklyOperationResponseVo {

    private String dayOfWeek;
    private int dayOfMonth;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Builder
    private WeeklyOperationResponseVo(String dayOfWeek,
                                     int dayOfMonth,
                                     LocalTime startTime,
                                     LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
