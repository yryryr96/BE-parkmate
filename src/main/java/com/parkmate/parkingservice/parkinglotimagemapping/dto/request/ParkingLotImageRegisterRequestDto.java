package com.parkmate.parkingservice.parkinglotimagemapping.dto.request;

import com.parkmate.parkingservice.parkinglotimagemapping.vo.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ParkingLotImageRegisterRequestDto {

    private String parkingLotUuid;
    private List<Image> imageUrls;

    @Builder
    private ParkingLotImageRegisterRequestDto(String parkingLotUuid,
                                             List<Image> imageUrls) {
        this.parkingLotUuid = parkingLotUuid;
        this.imageUrls = imageUrls;
    }

    public ParkingLotImageRegisterRequestDto withParkingLotUuid(String parkingLotUuid) {
        return ParkingLotImageRegisterRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .imageUrls(this.imageUrls)
                .build();
    }
}
