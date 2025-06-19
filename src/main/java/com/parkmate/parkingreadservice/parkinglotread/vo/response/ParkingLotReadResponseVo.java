package com.parkmate.parkingreadservice.parkinglotread.vo.response;

import com.parkmate.parkingreadservice.parkinglotread.domain.Image;
import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
public class ParkingLotReadResponseVo {

    private String parkingLotUuid;
    private String hostUuid;
    private String thumbnailUrl;
    private String name;
    private String phoneNumber;
    private String address;
    private double latitude;
    private double longitude;
    private int capacity;
    private String parkingLotType;
    private Set<String> parkingSpotTypes;
    private Set<String> evChargeTypes;
    private String extraInfo;
    private List<Image> imageUrls;
    private List<ParkingLotOption> options;
    private int likeCount;
    private int dislikeCount;

    @Builder
    private ParkingLotReadResponseVo(String parkingLotUuid,
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
                                     Set<String> evChargeTypes,
                                     String extraInfo,
                                     List<Image> imageUrls,
                                     List<ParkingLotOption> options,
                                     int likeCount,
                                     int dislikeCount) {
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
        this.evChargeTypes = evChargeTypes;
        this.extraInfo = extraInfo;
        this.imageUrls = imageUrls;
        this.options = options;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }
}
