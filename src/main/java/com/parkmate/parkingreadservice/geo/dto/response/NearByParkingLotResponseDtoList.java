package com.parkmate.parkingreadservice.geo.dto.response;

import com.parkmate.parkingreadservice.geo.vo.NearByParkingLotResponseVoList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NearByParkingLotResponseDtoList {

    private List<NearbyParkingLotResponseDto> parkingLots;

    @Builder
    private NearByParkingLotResponseDtoList(List<NearbyParkingLotResponseDto> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public static NearByParkingLotResponseDtoList from(List<NearbyParkingLotResponseDto> parkingLots) {
        return new NearByParkingLotResponseDtoList(parkingLots);
    }

    public NearByParkingLotResponseVoList toVo() {
        return NearByParkingLotResponseVoList.builder()
                .parkingLots(parkingLots.stream()
                        .map(NearbyParkingLotResponseDto::toVo)
                        .toList())
                .build();
    }
}

