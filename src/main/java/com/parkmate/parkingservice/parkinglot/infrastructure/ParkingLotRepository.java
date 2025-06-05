package com.parkmate.parkingservice.parkinglot.infrastructure;

import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

    Optional<ParkingLot> findByParkingLotUuidAndHostUuid(
            String parkingLotUuid,
            String hostUuid
    );

    Optional<ParkingLot> findByParkingLotUuid(String parkingLotUuid);

    @Query(value = "SELECT pl.hostUuid FROM ParkingLot pl WHERE pl.parkingLotUuid = :parkingLotUuid")
    Optional<String> findHostUuidByParkingLotUuid(String parkingLotUuid);
}
