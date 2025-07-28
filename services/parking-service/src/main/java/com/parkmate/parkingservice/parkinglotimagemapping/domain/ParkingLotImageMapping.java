package com.parkmate.parkingservice.parkinglotimagemapping.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingLotImageMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment( "주차장 UUID")
    @Column(nullable = false)
    private String parkingLotUuid;

    @Comment("이미지 URL")
    @Column(nullable = false)
    private String imageUrl;

    @Comment("이미지 순서")
    @Column(nullable = false)
    private Integer imageIndex;

    @Builder
    private ParkingLotImageMapping(Long id,
                                  String parkingLotUuid,
                                  String imageUrl,
                                  Integer imageIndex) {
        this.id = id;
        this.parkingLotUuid = parkingLotUuid;
        this.imageUrl = imageUrl;
        this.imageIndex = imageIndex;
    }
}
