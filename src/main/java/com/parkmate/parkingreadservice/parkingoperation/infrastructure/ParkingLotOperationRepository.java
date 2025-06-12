package com.parkmate.parkingreadservice.parkingoperation.infrastructure;

import com.parkmate.parkingreadservice.parkingoperation.domain.ParkingLotOperationRead;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParkingLotOperationRepository extends MongoRepository<ParkingLotOperationRead, String>, ParkingLotOperationMongoRepository {

}
