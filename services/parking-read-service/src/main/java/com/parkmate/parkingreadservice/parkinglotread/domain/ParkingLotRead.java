package com.parkmate.parkingreadservice.parkinglotread.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document(collection = "parking_lot_read")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingLotRead {

    @Id
    private String id;

    @Indexed(unique = true)
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
    private Boolean isEvChargingAvailable;
    private Set<String> evChargeTypes;
    private String extraInfo;
    private List<Image> imageUrls;
    private List<ParkingLotOption> options;
    private int likeCount = 0;
    private int dislikeCount = 0;
    private int reviewCount = 0;
    private double rating = 0.0;

    @Builder
    private ParkingLotRead(String parkingLotUuid,
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
                           int reviewCount,
                           double rating) {
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
        this.reviewCount = reviewCount;
        this.rating = rating;
    }
}
