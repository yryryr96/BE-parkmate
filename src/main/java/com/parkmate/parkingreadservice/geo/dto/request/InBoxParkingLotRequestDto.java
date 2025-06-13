package com.parkmate.parkingreadservice.geo.dto.request;

import com.parkmate.parkingreadservice.geo.vo.request.InBoxParkingLotRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

        return InBoxParkingLotRequestDto.builder()
                .swLat(inBoxParkingLotRequestVo.getSwLat())
                .swLng(inBoxParkingLotRequestVo.getSwLng())
                .neLat(inBoxParkingLotRequestVo.getNeLat())
                .neLng(inBoxParkingLotRequestVo.getNeLng())
                .startDate(inBoxParkingLotRequestVo.getStartDate())
                .endDate(inBoxParkingLotRequestVo.getEndDate())
                .build();
    }
}
