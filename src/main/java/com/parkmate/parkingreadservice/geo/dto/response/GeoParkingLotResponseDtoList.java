package com.parkmate.parkingreadservice.geo.dto.response;

import com.parkmate.parkingreadservice.geo.vo.response.GeoParkingLotResponseVoList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GeoParkingLotResponseDtoList {

    private List<GeoParkingLotResponseDto> parkingLots;

    @Builder
    private GeoParkingLotResponseDtoList(List<GeoParkingLotResponseDto> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public static GeoParkingLotResponseDtoList from(List<GeoParkingLotResponseDto> parkingLots) {
        return new GeoParkingLotResponseDtoList(parkingLots);
    }

    public GeoParkingLotResponseVoList toVo() {
        return GeoParkingLotResponseVoList.builder()
                .parkingLots(parkingLots.stream()
                        .map(GeoParkingLotResponseDto::toVo)
                        .toList())
                .build();
    }
}

