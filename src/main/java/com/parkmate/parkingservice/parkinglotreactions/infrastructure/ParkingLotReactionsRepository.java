package com.parkmate.parkingservice.parkinglotreactions.infrastructure;

import com.parkmate.parkingservice.parkinglotreactions.domain.ParkingLotReactions;
import com.parkmate.parkingservice.parkinglotreactions.domain.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParkingLotReactionsRepository extends JpaRepository<ParkingLotReactions, Long> {

    Optional<ParkingLotReactions> findByParkingLotUuidAndUserUuid(String parkingLotUuid, String userUuid);

    @Query(value = "SELECT pr.reactionType " +
                   "FROM ParkingLotReactions pr " +
                   "WHERE pr.parkingLotUuid = :parkingLotUuid AND pr.userUuid = :userUuid"
    )
    Optional<ReactionType> findReactionTypeByParkingLotUuidAndUserUuid(String parkingLotUuid, String userUuid);
}
