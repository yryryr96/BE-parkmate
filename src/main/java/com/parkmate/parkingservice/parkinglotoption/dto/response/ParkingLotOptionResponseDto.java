package com.parkmate.parkingservice.parkinglotoption.dto.response;

import com.parkmate.parkingservice.parkinglotoption.domain.ParkingLotOption;
import com.parkmate.parkingservice.parkinglotoption.vo.response.ParkingLotOptionResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotOptionResponseDto {

    private Long id;
    private String name;
    private String label;

    @Builder
    private ParkingLotOptionResponseDto(Long id,
                                        String name,
                                        String label) {
        this.id = id;
        this.name = name;
        this.label = label;
    }

    public static ParkingLotOptionResponseDto from(ParkingLotOption parkingLotOption) {
        return ParkingLotOptionResponseDto.builder()
                .id(parkingLotOption.getId())
                .name(parkingLotOption.getName())
                .label(parkingLotOption.getLabel())
                .build();
    }

    public ParkingLotOptionResponseVo toVo() {
        return ParkingLotOptionResponseVo.builder()
                .id(id)
                .name(name)
                .label(label)
                .build();
    }
}