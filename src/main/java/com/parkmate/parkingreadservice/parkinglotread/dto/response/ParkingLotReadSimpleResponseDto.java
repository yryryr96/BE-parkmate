package com.parkmate.parkingreadservice.parkinglotread.dto.response;

import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import com.parkmate.parkingreadservice.parkinglotread.vo.response.ParkingLotReadSimpleResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotReadSimpleResponseDto {

    private String parkingLotUuid;
    private String thumbnailUrl;
    private String name;
    private String address;
    private double rating;

    @Builder
    private ParkingLotReadSimpleResponseDto(String parkingLotUuid,
                                           String thumbnailUrl,
                                           String name,
                                           String address,
                                            double rating) {
        this.parkingLotUuid = parkingLotUuid;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.address = address;
        this.rating = rating;
    }

    public static ParkingLotReadSimpleResponseDto from(ParkingLotRead parkingLotRead) {
        return ParkingLotReadSimpleResponseDto.builder()
                .parkingLotUuid(parkingLotRead.getParkingLotUuid())
                .thumbnailUrl(parkingLotRead.getThumbnailUrl())
                .name(parkingLotRead.getName())
                .address(parkingLotRead.getAddress())
                .rating(parkingLotRead.getRating())
                .build();
    }

    public ParkingLotReadSimpleResponseVo toVo() {
        return ParkingLotReadSimpleResponseVo.builder()
                .parkingLotUuid(parkingLotUuid)
                .thumbnailUrl(thumbnailUrl)
                .name(name)
                .address(address)
                .rating(rating)
                .build();
    }
}
