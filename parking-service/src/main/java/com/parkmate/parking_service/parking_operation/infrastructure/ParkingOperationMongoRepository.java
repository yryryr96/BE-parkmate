package com.parkmate.parking_service.parking_operation.infrastructure;

import com.parkmate.parking_service.parking_operation.entity.ParkingOperation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ParkingOperationMongoRepository extends MongoRepository<ParkingOperation, String> {

    List<ParkingOperation> findAllByParkingLotUuidAndOperationDateBetween(
            String parkingLotUuid,
            LocalDate startDate,
            LocalDate endDate
    );

    Optional<ParkingOperation> findByParkingLotUuidAndParkingOperationUuid(
            String parkingLotUuid,
            String parkingOperationUuid
    );
}
