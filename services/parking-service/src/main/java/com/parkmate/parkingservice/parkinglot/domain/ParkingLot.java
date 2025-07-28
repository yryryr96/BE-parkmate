package com.parkmate.parkingservice.parkinglot.domain;

import com.parkmate.parkingservice.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingLot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("주차장 UUID")
    @Column(nullable = false, unique = true, length = 36)
    private String parkingLotUuid;

    @Comment("호스트 UUID")
    @Column(nullable = false, length = 36)
    private String hostUuid;

    @Enumerated(EnumType.STRING)
    @Comment("주차장 타입")
    @Column(nullable = false)
    private ParkingLotType parkingLotType;

    @Comment("주차장 이름")
    @Column(nullable = false, length = 100)
    private String name;

    @Comment("주차장 전화번호")
    @Column(nullable = false)
    private String phoneNumber;

    @Comment("주차장의 총 주차면 수")
    @Column(nullable = false)
    private int capacity;

    @Comment("우리 서비스에 등록된 주차면 수")
    @Column(nullable = false)
    private int registeredCapacity;

    @Comment("주차장 메인주소")
    @Column(nullable = false)
    private String mainAddress;

    @Comment("주차장 상세주소")
    @Column(nullable = false)
    private String detailAddress;

    @Comment("주차장 위도")
    @Column(nullable = false)
    private double latitude;

    @Comment("주차장 경도")
    @Column(nullable = false)
    private double longitude;

    @Comment("전기차 충전 가능 여부")
    @Column(nullable = false)
    private Boolean isEvChargingAvailable;

    @Comment("주차장 추가 정보")
    @Column(nullable = false)
    private String extraInfo;

    @Comment("썸네일 이미지 URL")
    @Column(nullable = true)
    private String thumbnailUrl;

    @Builder
    private ParkingLot(Long id,
                       String parkingLotUuid,
                       String hostUuid,
                       ParkingLotType parkingLotType,
                       String name,
                       String phoneNumber,
                       int capacity,
                       int registeredCapacity,
                       String mainAddress,
                       String detailAddress,
                       double latitude,
                       double longitude,
                       Boolean isEvChargingAvailable,
                       String extraInfo,
                       String thumbnailUrl) {
        this.id = id;
        this.parkingLotUuid = parkingLotUuid;
        this.hostUuid = hostUuid;
        this.parkingLotType = parkingLotType;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.capacity = capacity;
        this.registeredCapacity = registeredCapacity;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isEvChargingAvailable = isEvChargingAvailable;
        this.extraInfo = extraInfo;
        this.thumbnailUrl = thumbnailUrl;
    }
}
