package com.parkmate.parkingservice.parkinglot.dto.response;

import com.parkmate.parkingservice.parkinglot.vo.response.HostParkingLotResponseVoList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HostParkingLotResponseDtoList {

    private List<HostParkingLotResponseDto> parkingLots;

    @Builder
    private HostParkingLotResponseDtoList(List<HostParkingLotResponseDto> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public static HostParkingLotResponseDtoList from(List<HostParkingLotResponseDto> parkingLots) {
        return HostParkingLotResponseDtoList.builder()
                .parkingLots(parkingLots)
                .build();
    }

    public HostParkingLotResponseVoList toVo() {
        return HostParkingLotResponseVoList.builder()
                .parkingLots(parkingLots.stream()
                        .map(HostParkingLotResponseDto::toVo)
                        .toList())
                .build();
    }
}
