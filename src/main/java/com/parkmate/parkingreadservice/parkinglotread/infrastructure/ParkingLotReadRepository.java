package com.parkmate.parkingreadservice.parkinglotread.infrastructure;

import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParkingLotReadRepository extends MongoRepository<ParkingLotRead, String> {
}
