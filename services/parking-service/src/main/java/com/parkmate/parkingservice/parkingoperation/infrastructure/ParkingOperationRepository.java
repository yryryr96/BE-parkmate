package com.parkmate.parkingservice.parkingoperation.infrastructure;

import com.parkmate.parkingservice.parkingoperation.domain.ParkingOperation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ParkingOperationRepository extends MongoRepository<ParkingOperation, String>, CustomOperationRepository {

    List<ParkingOperation> findAllByParkingLotUuidAndOperationDateBetween(String parkingLotUuid,
                                                                          LocalDate startDate,
                                                                          LocalDate endDate
    );

    Optional<ParkingOperation> findByParkingLotUuidAndParkingOperationUuid(String parkingLotUuid,
                                                                           String parkingOperationUuid
    );

    void deleteByParkingLotUuidAndParkingOperationUuid(
            String parkingLotUuid,
            String parkingOperationUuid
    );

    Optional<ParkingOperation> findByParkingLotUuidAndOperationDate(String parkingLotUuid,
                                                                    LocalDateTime operationDate);
}
