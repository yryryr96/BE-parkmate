package com.parkmate.parkingreadservice.geo.dto.request;

import com.parkmate.parkingreadservice.geo.vo.request.InBoxParkingLotRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class InBoxParkingLotRequestDto {

    private double swLat;
    private double swLng;
    private double neLat;
    private double neLng;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    private InBoxParkingLotRequestDto(double swLat,
                                      double swLng,
                                      double neLat,
                                      double neLng,
                                      LocalDateTime startDate,
                                      LocalDateTime endDate) {
        this.swLat = swLat;
        this.swLng = swLng;
        this.neLat = neLat;
        this.neLng = neLng;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static InBoxParkingLotRequestDto of(InBoxParkingLotRequestVo inBoxParkingLotRequestVo) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = inBoxParkingLotRequestVo.getStartDate() == null ? now : inBoxParkingLotRequestVo.getStartDate();
        LocalDateTime endDate = inBoxParkingLotRequestVo.getEndDate() == null ? LocalDateTime.of(now.toLocalDate(),
                LocalTime.of(23, 59,59)) : inBoxParkingLotRequestVo.getEndDate();

        return InBoxParkingLotRequestDto.builder()
                .swLat(inBoxParkingLotRequestVo.getSwLat())
                .swLng(inBoxParkingLotRequestVo.getSwLng())
                .neLat(inBoxParkingLotRequestVo.getNeLat())
                .neLng(inBoxParkingLotRequestVo.getNeLng())
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
