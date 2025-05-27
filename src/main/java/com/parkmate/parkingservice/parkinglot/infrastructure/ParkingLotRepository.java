package com.parkmate.parkingservice.parkinglot.infrastructure;

import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

    Optional<ParkingLot> findByParkingLotUuidAndHostUuid(
            String parkingLotUuid,
            String hostUuid
    );

    Optional<ParkingLot> findByParkingLotUuid(String parkingLotUuid);
}
