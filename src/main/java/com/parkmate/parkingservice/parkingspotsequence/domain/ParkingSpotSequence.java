package com.parkmate.parkingservice.parkingspotsequence.domain;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingSpotSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("주차장 UUID")
    @Column(nullable = false, length = 36)
    private String parkingLotUuid;

    @Enumerated(EnumType.STRING)
    @Comment("주차공간 타입")
    @Column(nullable = false)
    private ParkingSpotType parkingSpotType;

    @Comment("시퀀스 번호")
    @Column(nullable = false)
    private Long sequence;

    @Builder
    private ParkingSpotSequence(Long id,
                               String parkingLotUuid,
                               ParkingSpotType parkingSpotType,
                               Long sequence) {
        this.id = id;
        this.parkingLotUuid = parkingLotUuid;
        this.parkingSpotType = parkingSpotType;
        this.sequence = sequence;
    }

    public void updateSequence(Long sequence) {
        this.sequence = sequence;
    }
}
