package com.parkmate.parkingservice.parkingspot.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("주차면 이름")
    @Column(nullable = false, length = 100)
    private String name;

    @Comment("주차장 UUID")
    @Column(nullable = false)
    private String parkingLotUuid;

    @Enumerated(EnumType.STRING)
    @Comment("주차면 차량 타입")
    @Column(nullable = false)
    private ParkingSpotType type;

    @Comment("전기차 충전 가능 여부")
    @Column(nullable = false)
    private Boolean isEvChargingAvailable;

    @ElementCollection(targetClass = EvChargeType.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "parking_spot_charging_type",
            joinColumns = @JoinColumn(name = "id")
    )
    @Column(name = "ev_charge_type")
    private Set<EvChargeType> EvChargeTypes = new HashSet<>();

    @Builder
    private ParkingSpot(Long id,
                       String name,
                       String parkingLotUuid,
                       ParkingSpotType type,
                       Boolean isEvChargingAvailable,
                       Set<EvChargeType> evChargeTypes) {
        this.id = id;
        this.name = name;
        this.parkingLotUuid = parkingLotUuid;
        this.type = type;
        this.isEvChargingAvailable = isEvChargingAvailable;
        EvChargeTypes = evChargeTypes;
    }
}
