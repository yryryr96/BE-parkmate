package com.parkmate.parkingreadservice.geo.dto.response;

import com.parkmate.parkingreadservice.geo.vo.response.InBoxParkingLotResponseList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InBoxParkingLotResponseDtoList {

    private List<InBoxParkingLotResponseDto> parkingLots;

    @Builder
    private InBoxParkingLotResponseDtoList(List<InBoxParkingLotResponseDto> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public static InBoxParkingLotResponseDtoList from(List<InBoxParkingLotResponseDto> parkingLots) {
        return new InBoxParkingLotResponseDtoList(parkingLots);
    }

    public InBoxParkingLotResponseList toVo() {
        return InBoxParkingLotResponseList.builder()
                .parkingLots(parkingLots.stream()
                        .map(InBoxParkingLotResponseDto::toVo)
                        .toList())
                .build();
    }
}

