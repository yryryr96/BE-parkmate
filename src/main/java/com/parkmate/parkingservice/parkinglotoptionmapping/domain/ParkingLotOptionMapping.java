package com.parkmate.parkingservice.parkinglotoptionmapping.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingLotOptionMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("주차장 UUID")
    @Column(nullable = false, length = 36)
    private String parkingLotUuid;

    @Comment("옵션 ID")
    @Column(nullable = false)
    private Long optionId;

    @Builder
    private ParkingLotOptionMapping(Long id,
                                    String parkingLotUuid,
                                    Long optionId) {
        this.id = id;
        this.parkingLotUuid = parkingLotUuid;
        this.optionId = optionId;
    }
}
