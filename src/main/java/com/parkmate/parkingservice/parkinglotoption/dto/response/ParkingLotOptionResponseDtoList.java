package com.parkmate.parkingservice.parkinglotoption.dto.response;

import com.parkmate.parkingservice.parkinglotoption.vo.response.ParkingLotOptionResponseVoList;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ParkingLotOptionResponseDtoList {

    private List<ParkingLotOptionResponseDto> options;

    private ParkingLotOptionResponseDtoList(List<ParkingLotOptionResponseDto> options) {
        this.options = options;
    }

    public static ParkingLotOptionResponseDtoList of(List<ParkingLotOptionResponseDto> options) {
        return new ParkingLotOptionResponseDtoList(options);
    }

    public ParkingLotOptionResponseVoList toVo() {
        return ParkingLotOptionResponseVoList.builder()
                .options(options.stream()
                        .map(ParkingLotOptionResponseDto::toVo)
                        .toList())
                .build();
    }
}
