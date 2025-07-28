package com.parkmate.parkingreadservice.geo.dto.response;

import com.parkmate.parkingreadservice.geo.vo.response.InBoxParkingLotResponseVo;
import com.parkmate.parkingreadservice.parkinglotread.domain.Image;
import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotReadResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InBoxParkingLotResponseDto {

    private String parkingLotUuid;
    private String name;
    private String address;
    private String thumbnailUrl;
    private List<Image> imageUrls;
    private double latitude;
    private double longitude;
    private double distance;
    private int availableSpotCount;
    private int likeCount;
    private int dislikeCount;
    private double rating;
    private long reviewCount;

    @Builder
    private InBoxParkingLotResponseDto(String parkingLotUuid,
                                       String name,
                                       String address,
                                       String thumbnailUrl,
                                       List<Image> imageUrls,
                                       double latitude,
                                       double longitude,
                                       double distance,
                                       int availableSpotCount,
                                       int likeCount,
                                       int dislikeCount,
                                       double rating,
                                       long reviewCount) {
        this.parkingLotUuid = parkingLotUuid;
        this.name = name;
        this.address = address;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrls = imageUrls;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.availableSpotCount = availableSpotCount;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }

    public static InBoxParkingLotResponseDto of(ParkingLotReadResponseDto parkingLotReadResponseDto,
                                                double latitude,
                                                double longitude,
                                                double distance) {
        return InBoxParkingLotResponseDto.builder()
                .parkingLotUuid(parkingLotReadResponseDto.getParkingLotUuid())
                .name(parkingLotReadResponseDto.getName())
                .address(parkingLotReadResponseDto.getAddress())
                .thumbnailUrl(parkingLotReadResponseDto.getThumbnailUrl())
                .imageUrls(parkingLotReadResponseDto.getImageUrls())
                .latitude(latitude)
                .longitude(longitude)
                .distance(distance)
                .availableSpotCount(parkingLotReadResponseDto.getCapacity())
                .likeCount(parkingLotReadResponseDto.getLikeCount())
                .dislikeCount(parkingLotReadResponseDto.getDislikeCount())
                .rating(parkingLotReadResponseDto.getRating())
                .reviewCount(parkingLotReadResponseDto.getReviewCount())
                .build();
    }

    public InBoxParkingLotResponseVo toVo() {
        return InBoxParkingLotResponseVo.builder()
                .parkingLotUuid(parkingLotUuid)
                .name(name)
                .address(address)
                .thumbnailUrl(thumbnailUrl)
                .imageUrls(imageUrls)
                .latitude(latitude)
                .longitude(longitude)
                .distance(distance)
                .availableSpotCount(availableSpotCount)
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .rating(rating)
                .reviewCount(reviewCount)
                .build();
    }
}
