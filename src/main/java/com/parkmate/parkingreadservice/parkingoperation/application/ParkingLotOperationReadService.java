package com.parkmate.parkingreadservice.parkingoperation.application;

import com.parkmate.parkingreadservice.kafka.event.OperationCreateEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface ParkingLotOperationReadService {

    void create(OperationCreateEvent operationCreateEvent);

    Set<String> findAvailableParkingLotUuids(
            List<String> parkingLotUuids,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    );
}
