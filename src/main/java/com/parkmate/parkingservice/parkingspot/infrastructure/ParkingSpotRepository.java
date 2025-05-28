package com.parkmate.parkingservice.parkingspot.infrastructure;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    void deleteByIdAndParkingLotUuid(
            Long parkingSpotId,
            String parkingLotUuid
    );
}
