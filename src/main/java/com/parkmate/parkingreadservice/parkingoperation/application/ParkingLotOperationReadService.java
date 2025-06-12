package com.parkmate.parkingreadservice.parkingoperation.application;

import com.parkmate.parkingreadservice.kafka.event.OperationCreateEvent;

public interface ParkingLotOperationReadService {

    void create(OperationCreateEvent operationCreateEvent);
}
