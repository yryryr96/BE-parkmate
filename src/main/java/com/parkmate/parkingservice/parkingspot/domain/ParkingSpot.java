package com.parkmate.parkingservice.parkingspot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("주차면 이름")
    private String name;
    
    @Comment("주차장 UUID")
    private String parkingLotUuid;
    
    @Comment("주차면 차량 타입")
    private ParkingSpotType type;
    
    @Comment("전기차 충전 가능 여부")
    private Boolean isEvChargingAvailable;

    @Builder
    private ParkingSpot(String name, String parkingLotUuid, ParkingSpotType type, Boolean isEvChargingAvailable) {
        this.name = name;
        this.parkingLotUuid = parkingLotUuid;
        this.type = type;
        this.isEvChargingAvailable = isEvChargingAvailable;
    }
}
