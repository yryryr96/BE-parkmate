package com.parkmate.parkingreadservice.parkingoperation.infrastructure;

import com.parkmate.parkingreadservice.kafka.event.OperationCreateEvent;
import com.parkmate.parkingreadservice.parkingoperation.domain.ParkingLotOperationRead;

import java.time.LocalDate;
import java.util.List;

public interface ParkingLotOperationMongoRepository {

    void create(OperationCreateEvent operationCreateEvent);

    List<ParkingLotOperationRead> findAvailableParkingLotUuids(List<String> parkingLotUuids,
                                                               List<LocalDate> dates);

}
