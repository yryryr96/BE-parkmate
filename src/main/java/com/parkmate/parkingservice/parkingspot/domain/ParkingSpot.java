package com.parkmate.parkingservice.parkingspot.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_spot_id")
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

    @ElementCollection(targetClass = EvChargeType.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "parking_spot_charging_type",
            joinColumns = @JoinColumn(name = "parking_spot_id")
    )
    @Comment("전기차 충전 타입")
    private Set<EvChargeType> evChargeTypes;

    @Builder
    private ParkingSpot(Long id,
                        String name,
                        String parkingLotUuid,
                        ParkingSpotType type,
                        Set<EvChargeType> evChargeTypes) {
        this.id = id;
        this.name = name;
        this.parkingLotUuid = parkingLotUuid;
        this.type = type;
        this.evChargeTypes = evChargeTypes;
    }
}
