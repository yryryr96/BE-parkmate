package com.parkmate.parkingservice.parkinglot.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingLotGeoLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Point location;

    @Column(nullable = false, unique = true, length = 36)
    private String parkingLotUuid;

    @Builder
    private ParkingLotGeoLocation(Long id,
                                 Point location,
                                 String parkingLotUuid) {
        this.id = id;
        this.location = location;
        this.parkingLotUuid = parkingLotUuid;
    }
}
