package com.parkmate.parkingreadservice.parkinglot.domain;

import com.parkmate.parkingreadservice.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
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

    private String isEvChargingAvailable;

    // enum이나 entity로 뺴기
    private Set<String> evChargeTypes;

    // 경차, 중형, 대형
    private Set<String> parkingLotTypes;

    private String extraInfo;

    private List<String> imageUrls;

    private int likeCount;

    private int dislikeCount;

}
