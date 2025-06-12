package com.parkmate.parkingreadservice.parkingoperation.application;

import com.parkmate.parkingreadservice.kafka.event.OperationCreateEvent;
import com.parkmate.parkingreadservice.parkingoperation.dto.response.ParkingLotOperationResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ParkingLotOperationReadService {

    void create(OperationCreateEvent operationCreateEvent);

    List<ParkingLotOperationResponseDto> getOperationsByUuidAndDateRange(
            List<String> parkingLotUuids,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    );
}
