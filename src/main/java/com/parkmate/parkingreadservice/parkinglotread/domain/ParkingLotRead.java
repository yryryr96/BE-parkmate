package com.parkmate.parkingreadservice.parkinglotread.domain;

import com.parkmate.parkingreadservice.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingLotRead extends BaseEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String parkingLotUuid;
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
    private List<ParkingLotOption> options;
    private int likeCount;
    private int dislikeCount;

    @Builder
    private ParkingLotRead(String id,
                          String parkingLotUuid,
                          String hostUuid,
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
                           List<ParkingLotOption> options,
                          int likeCount,
                          int dislikeCount) {
        this.id = id;
        this.parkingLotUuid = parkingLotUuid;
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
        this.options = options;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }
}
