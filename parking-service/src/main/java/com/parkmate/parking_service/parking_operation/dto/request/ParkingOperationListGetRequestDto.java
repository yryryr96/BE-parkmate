package com.parkmate.parking_service.parking_operation.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ParkingOperationListGetRequestDto {

    private String parkingLotUuid;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    private ParkingOperationListGetRequestDto(String parkingLotUuid,
                                              LocalDate startDate,
                                              LocalDate endDate) {
        this.parkingLotUuid = parkingLotUuid;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static ParkingOperationListGetRequestDto of(String parkingLotUuid,
                                                       Integer year,
                                                       Integer month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        return ParkingOperationListGetRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
