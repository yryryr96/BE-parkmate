package com.parkmate.parkingreadservice.parkinglotread.vo.response;

import com.parkmate.parkingreadservice.parkinglotread.vo.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
public class ParkingLotReadResponseVo {

    private String hostUuid;
    private Image thumbnailUrl;
    private String name;
    private String phoneNumber;
    private String address;
    private String parkingLotType;
    private Set<String> parkingSpotTypes;
    private Boolean isEvChargingAvailable;
    private Set<String> evChargeTypes;
    private String extraInfo;
    private List<Image> imageUrls;
    private int likeCount;
    private int dislikeCount;

    @Builder
    private ParkingLotReadResponseVo(String hostUuid,
                                     Image thumbnailUrl,
                                    String name,
                                    String phoneNumber,
                                    String address,
                                    String parkingLotType,
                                    Set<String> parkingSpotTypes,
                                    Boolean isEvChargingAvailable,
                                    Set<String> evChargeTypes,
                                    String extraInfo,
                                    List<Image> imageUrls,
                                    int likeCount,
                                    int dislikeCount) {
        this.hostUuid = hostUuid;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.parkingLotType = parkingLotType;
        this.parkingSpotTypes = parkingSpotTypes;
        this.isEvChargingAvailable = isEvChargingAvailable;
        this.evChargeTypes = evChargeTypes;
        this.extraInfo = extraInfo;
        this.imageUrls = imageUrls;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }
}
