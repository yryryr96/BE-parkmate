package com.parkmate.parkingservice.parkinglotimagemapping.dto.response;

import com.parkmate.parkingservice.parkinglotimagemapping.domain.ParkingLotImageMapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotImageMappingResponseDto {

    private String parkingLotUuid;
    private String imageUrl;
    private Integer imageIndex;

    @Builder
    private ParkingLotImageMappingResponseDto(String parkingLotUuid,
                                             String imageUrl,
                                             Integer imageIndex) {
        this.parkingLotUuid = parkingLotUuid;
        this.imageUrl = imageUrl;
        this.imageIndex = imageIndex;
    }

    public static ParkingLotImageMappingResponseDto from(ParkingLotImageMapping parkingLotImageMapping) {
        return ParkingLotImageMappingResponseDto.builder()
                .parkingLotUuid(parkingLotImageMapping.getParkingLotUuid())
                .imageUrl(parkingLotImageMapping.getImageUrl())
                .imageIndex(parkingLotImageMapping.getImageIndex())
                .build();
    }
}
