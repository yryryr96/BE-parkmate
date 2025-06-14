package com.parkmate.parkingreadservice.parkinglotread.infrastructure;

import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ParkingLotReadRepository extends MongoRepository<ParkingLotRead, String>, CustomParkingLotRepository {

    Optional<ParkingLotRead> findByParkingLotUuid(String parkingLotUuid);
}
