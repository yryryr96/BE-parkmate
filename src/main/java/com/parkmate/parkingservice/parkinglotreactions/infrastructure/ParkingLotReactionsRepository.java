package com.parkmate.parkingservice.parkinglotreactions.infrastructure;

import com.parkmate.parkingservice.parkinglotreactions.domain.ParkingLotReactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingLotReactionsRepository extends JpaRepository<ParkingLotReactions, Long> {

    Optional<ParkingLotReactions> findByParkingLotUuidAndUserUuid(String parkingLotUuid, String userUuid);
}
