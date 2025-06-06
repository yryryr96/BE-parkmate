package com.parkmate.parkingreadservice.parkinglotread.infrastructure;

import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ParkingLotReadRepository extends CrudRepository<ParkingLotRead, String>, CustomMongoRepository {

    Optional<ParkingLotRead> findByParkingLotUuid(String parkingLotUuid);
}
