package com.parkmate.parking_service.parkinglot.infrastructure.mysql;

import com.parkmate.parking_service.parkinglot.domain.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

    Optional<ParkingLot> findByParkingLotUuidAndHostUuid(
            String parkingLotUuid,
            String hostUuid
    );
}
