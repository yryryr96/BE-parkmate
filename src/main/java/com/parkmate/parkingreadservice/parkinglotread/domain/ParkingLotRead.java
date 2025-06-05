package com.parkmate.parkingreadservice.parkinglotread.domain;

import com.parkmate.parkingreadservice.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingLotRead extends BaseEntity {

    @Id
    private String parkingLotUuid;
    private String thumbnailUrl;
    private String name;
    private String phoneNumber;
    private String address;
    private Boolean isEvChargingAvailable;
    private String parkingLotType;
    private Set<String> parkingSpotTypes;
    private Set<String> evChargeTypes;
    private String extraInfo;
    private List<String> imageUrls;
    private int likeCount;
    private int dislikeCount;

    @Version
    private Integer version;

    @Builder
    private ParkingLotRead(String parkingLotUuid,
                           String thumbnailUrl,
                           String name,
                           String phoneNumber,
                           String address,
                           Boolean isEvChargingAvailable,
                           String parkingLotType,
                           Set<String> evChargeTypes,
                           Set<String> parkingSpotTypes,
                           String extraInfo,
                           List<String> imageUrls,
                           int likeCount,
                           int dislikeCount) {
        this.parkingLotUuid = parkingLotUuid;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isEvChargingAvailable = isEvChargingAvailable;
        this.evChargeTypes = evChargeTypes;
        this.parkingLotType = parkingLotType;
        this.parkingSpotTypes = parkingSpotTypes;
        this.extraInfo = extraInfo;
        this.imageUrls = imageUrls;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }
}
