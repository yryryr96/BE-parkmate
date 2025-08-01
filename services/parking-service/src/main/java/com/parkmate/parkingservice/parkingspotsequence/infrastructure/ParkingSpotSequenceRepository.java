package com.parkmate.parkingservice.parkingspotsequence.infrastructure;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import com.parkmate.parkingservice.parkingspotsequence.domain.ParkingSpotSequence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingSpotSequenceRepository extends JpaRepository<ParkingSpotSequence, Long> {

    Optional<ParkingSpotSequence> findByParkingLotUuidAndParkingSpotType(String parkingLotUuid,
                                                                         ParkingSpotType parkingSpotType
    );
}
