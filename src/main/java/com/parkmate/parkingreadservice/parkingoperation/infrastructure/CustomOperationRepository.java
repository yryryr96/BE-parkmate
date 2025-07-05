package com.parkmate.parkingreadservice.parkingoperation.infrastructure;

import com.parkmate.parkingreadservice.kafka.event.parkinglot.OperationCreateEvent;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomOperationRepository {

    void create(OperationCreateEvent operationCreateEvent);

    List<String> findOperatingUuidsByTimeRange(List<String> strings,
                                               LocalDateTime date,
                                               LocalDateTime dailyStartTime,
                                               LocalDateTime dailyEndTime);
}
