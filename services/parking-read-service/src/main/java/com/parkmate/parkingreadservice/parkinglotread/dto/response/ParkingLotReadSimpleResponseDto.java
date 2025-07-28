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
    private int reviewCount;

    @Builder
    private ParkingLotReadSimpleResponseDto(String parkingLotUuid,
                                            String thumbnailUrl,
                                            String name,
                                            String address,
                                            double rating,
                                            int reviewCount) {
        this.parkingLotUuid = parkingLotUuid;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }

    public static ParkingLotReadSimpleResponseDto from(ParkingLotRead parkingLotRead) {
        return ParkingLotReadSimpleResponseDto.builder()
                .parkingLotUuid(parkingLotRead.getParkingLotUuid())
                .thumbnailUrl(parkingLotRead.getThumbnailUrl())
                .name(parkingLotRead.getName())
                .address(parkingLotRead.getAddress())
                .rating(parkingLotRead.getRating())
                .reviewCount(parkingLotRead.getReviewCount())
                .build();
    }

    public ParkingLotReadSimpleResponseVo toVo() {
        return ParkingLotReadSimpleResponseVo.builder()
                .parkingLotUuid(parkingLotUuid)
                .thumbnailUrl(thumbnailUrl)
                .name(name)
                .address(address)
                .rating(rating)
                .reviewCount(reviewCount)
                .build();
    }
}
