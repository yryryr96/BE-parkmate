package com.parkmate.parkingservice.parkingspot.infrastructure;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    void deleteByIdAndParkingLotUuid(
            Long parkingSpotId,
            String parkingLotUuid
    );

    Optional<ParkingSpot> findByIdAndParkingLotUuid(Long parkingSpotId,
                                                    String parkingLotUuid);

    List<ParkingSpot> findAllByParkingLotUuid(String parkingLotUuid);
}
