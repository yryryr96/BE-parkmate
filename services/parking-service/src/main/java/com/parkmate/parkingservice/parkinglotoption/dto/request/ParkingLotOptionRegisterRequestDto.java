package com.parkmate.parkingservice.parkinglotoption.dto.request;

import com.parkmate.parkingservice.parkinglotoption.domain.ParkingLotOption;
import com.parkmate.parkingservice.parkinglotoption.vo.request.ParkingLotOptionRegisterRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotOptionRegisterRequestDto {

    private String name;
    private String label;

    @Builder
    private ParkingLotOptionRegisterRequestDto(String name,
                                              String label) {
        this.name = name;
        this.label = label;
    }

    public static ParkingLotOptionRegisterRequestDto from(ParkingLotOptionRegisterRequestVo parkingLotOptionRegisterRequestVo) {
        return ParkingLotOptionRegisterRequestDto.builder()
                .name(parkingLotOptionRegisterRequestVo.getName())
                .label(parkingLotOptionRegisterRequestVo.getLabel())
                .build();
    }

    public ParkingLotOption toEntity() {
        return ParkingLotOption.builder()
                .name(name)
                .label(label)
                .build();
    }
}
