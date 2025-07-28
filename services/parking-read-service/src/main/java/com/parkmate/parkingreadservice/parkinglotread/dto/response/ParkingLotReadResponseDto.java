package com.parkmate.parkingreadservice.parkinglotread.dto.response;

import com.parkmate.parkingreadservice.parkinglotread.domain.Image;
import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotOption;
import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import com.parkmate.parkingreadservice.parkinglotread.vo.response.ParkingLotReadResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
public class ParkingLotReadResponseDto {

    private String parkingLotUuid;
    private String hostUuid;
    private String name;
    private String phoneNumber;
    private String address;
    private double latitude;
    private double longitude;
    private int capacity;
    private String parkingLotType;
    private Set<String> parkingSpotTypes;
    private Boolean isEvChargingAvailable;
    private Set<String> evChargeTypes;
    private String extraInfo;
    private String thumbnailUrl;
    private List<Image> imageUrls;
    private List<ParkingLotOption> options;
    private int likeCount;
    private int dislikeCount;
    private double rating;
    private int reviewCount;

    @Builder
    private ParkingLotReadResponseDto(String parkingLotUuid,
                                      String hostUuid,
                                      String thumbnailUrl,
                                      String name,
                                      String phoneNumber,
                                      String address,
                                      double latitude,
                                      double longitude,
                                      int capacity,
                                      String parkingLotType,
                                      Set<String> parkingSpotTypes,
                                      Boolean isEvChargingAvailable,
                                      Set<String> evChargeTypes,
                                      String extraInfo,
                                      List<Image> imageUrls,
                                      List<ParkingLotOption> options,
                                      int likeCount,
                                      int dislikeCount,
                                      double rating,
                                      int reviewCount) {
        this.parkingLotUuid = parkingLotUuid;
        this.hostUuid = hostUuid;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
        this.parkingLotType = parkingLotType;
        this.parkingSpotTypes = parkingSpotTypes;
        this.isEvChargingAvailable = isEvChargingAvailable;
        this.evChargeTypes = evChargeTypes;
        this.extraInfo = extraInfo;
        this.imageUrls = imageUrls;
        this.options = options;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }

    public static ParkingLotReadResponseDto from(ParkingLotRead parkingLotRead) {
        return ParkingLotReadResponseDto.builder()
                .parkingLotUuid(parkingLotRead.getParkingLotUuid())
                .hostUuid(parkingLotRead.getHostUuid())
                .thumbnailUrl(parkingLotRead.getThumbnailUrl())
                .name(parkingLotRead.getName())
                .phoneNumber(parkingLotRead.getPhoneNumber())
                .address(parkingLotRead.getAddress())
                .latitude(parkingLotRead.getLatitude())
                .longitude(parkingLotRead.getLongitude())
                .capacity(parkingLotRead.getCapacity())
                .parkingLotType(parkingLotRead.getParkingLotType())
                .parkingSpotTypes(parkingLotRead.getParkingSpotTypes())
                .isEvChargingAvailable(parkingLotRead.getIsEvChargingAvailable())
                .evChargeTypes(parkingLotRead.getEvChargeTypes())
                .extraInfo(parkingLotRead.getExtraInfo())
                .imageUrls(parkingLotRead.getImageUrls())
                .options(parkingLotRead.getOptions())
                .likeCount(parkingLotRead.getLikeCount())
                .dislikeCount(parkingLotRead.getDislikeCount())
                .rating(parkingLotRead.getRating())
                .reviewCount(parkingLotRead.getReviewCount())
                .build();
    }

    public ParkingLotReadResponseVo toVo() {
        return ParkingLotReadResponseVo.builder()
                .parkingLotUuid(parkingLotUuid)
                .hostUuid(hostUuid)
                .thumbnailUrl(thumbnailUrl)
                .name(name)
                .phoneNumber(phoneNumber)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .capacity(capacity)
                .parkingLotType(parkingLotType)
                .parkingSpotTypes(parkingSpotTypes)
                .evChargeTypes(evChargeTypes)
                .extraInfo(extraInfo)
                .imageUrls(imageUrls)
                .options(options)
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .rating(rating)
                .reviewCount(reviewCount)
                .build();
    }

    public ParkingLotReadResponseDto changeCapacity(int capacity) {
        return ParkingLotReadResponseDto.builder()
                .parkingLotUuid(this.parkingLotUuid)
                .hostUuid(this.hostUuid)
                .thumbnailUrl(this.thumbnailUrl)
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .capacity(capacity)
                .parkingLotType(this.parkingLotType)
                .parkingSpotTypes(this.parkingSpotTypes)
                .isEvChargingAvailable(this.isEvChargingAvailable)
                .evChargeTypes(this.evChargeTypes)
                .extraInfo(this.extraInfo)
                .imageUrls(this.imageUrls)
                .options(this.options)
                .likeCount(this.likeCount)
                .dislikeCount(this.dislikeCount)
                .rating(this.rating)
                .reviewCount(this.reviewCount)
                .build();
    }
}
