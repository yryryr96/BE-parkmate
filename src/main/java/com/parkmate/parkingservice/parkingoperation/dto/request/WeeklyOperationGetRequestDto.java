package com.parkmate.parkingservice.parkingoperation.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class WeeklyOperationGetRequestDto {

    private String parkingLotUuid;
    private LocalDate startDate;

    @Builder
    private WeeklyOperationGetRequestDto(String parkingLotUuid,
                                         LocalDate startDate) {
        this.parkingLotUuid = parkingLotUuid;
        this.startDate = startDate;
    }

    public static WeeklyOperationGetRequestDto of(String parkingLotUuid,
                                                  LocalDateTime startDateTime) {
        return new WeeklyOperationGetRequestDto(parkingLotUuid, startDateTime.toLocalDate());
    }
}
