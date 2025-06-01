package com.parkmate.parkingservice.parkinglotimagemapping.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ParkingLotImageRegisterRequestDto {

    private String parkingLotUuid;
    private List<String> imageUrls;

    @Builder
    private ParkingLotImageRegisterRequestDto(String parkingLotUuid,
                                             List<String> imageUrls) {
        this.parkingLotUuid = parkingLotUuid;
        this.imageUrls = imageUrls;
    }

    public static ParkingLotImageRegisterRequestDto of(String parkingLotUuid,
                                                       List<String> imageUrls) {
        return ParkingLotImageRegisterRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .imageUrls(imageUrls)
                .build();
    }
}
