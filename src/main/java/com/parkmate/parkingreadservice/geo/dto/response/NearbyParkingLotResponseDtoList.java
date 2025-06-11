package com.parkmate.parkingreadservice.geo.dto.response;

import com.parkmate.parkingreadservice.geo.vo.NearbyParkingLotResponseVoList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NearbyParkingLotResponseDtoList {

    private List<NearbyParkingLotResponseDto> parkingLots;

    @Builder
    private NearbyParkingLotResponseDtoList(List<NearbyParkingLotResponseDto> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public static NearbyParkingLotResponseDtoList from(List<NearbyParkingLotResponseDto> parkingLots) {
        return new NearbyParkingLotResponseDtoList(parkingLots);
    }

    public NearbyParkingLotResponseVoList toVo() {
        return NearbyParkingLotResponseVoList.builder()
                .parkingLots(parkingLots.stream()
                        .map(NearbyParkingLotResponseDto::toVo)
                        .toList())
                .build();
    }
}

