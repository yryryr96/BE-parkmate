package com.parkmate.parkingservice.parkingspotsequence.infrastructure;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import com.parkmate.parkingservice.parkingspotsequence.domain.ParkingSpotSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParkingSpotSequenceRepository extends JpaRepository<ParkingSpotSequence, Long> {

    @Query(value = "SELECT pss.sequence " +
            "FROM ParkingSpotSequence pss " +
            "WHERE pss.parkingLotUuid = :parkingLotUuid AND " +
            "pss.parkingSpotType = :parkingSpotType")
    Optional<Long> getRegularSpotSequence(
            String parkingLotUuid,
            ParkingSpotType parkingSpotType
    );

    Optional<ParkingSpotSequence> findByParkingLotUuidAndParkingSpotType(
            String parkingLotUuid,
            ParkingSpotType parkingSpotType
    );
}
