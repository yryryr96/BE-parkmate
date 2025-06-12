package com.parkmate.parkingreadservice.kafka.eventmanager;

import com.parkmate.parkingreadservice.kafka.event.OperationCreateEvent;
import com.parkmate.parkingreadservice.parkingoperation.application.ParkingLotOperationReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OperationEventManager {

    private final ParkingLotOperationReadService parkingLotOperationReadService;

    public void handleOperationCreatedEvent(OperationCreateEvent operationCreatedEvent) {

        parkingLotOperationReadService.create(operationCreatedEvent);
        log.info("received OperationCreatedEvent for parking lot: {}",
                operationCreatedEvent.getParkingLotUuid());
    }
}
