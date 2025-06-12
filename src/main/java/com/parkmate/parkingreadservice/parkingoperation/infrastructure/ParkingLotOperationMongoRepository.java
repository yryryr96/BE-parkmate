package com.parkmate.parkingreadservice.parkingoperation.infrastructure;

import com.parkmate.parkingreadservice.kafka.event.OperationCreateEvent;

public interface ParkingLotOperationMongoRepository {

    void create(OperationCreateEvent operationCreateEvent);
}
