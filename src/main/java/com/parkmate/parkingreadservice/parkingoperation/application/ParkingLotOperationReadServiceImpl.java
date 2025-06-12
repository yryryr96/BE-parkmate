package com.parkmate.parkingreadservice.parkingoperation.application;

import com.parkmate.parkingreadservice.kafka.event.OperationCreateEvent;
import com.parkmate.parkingreadservice.parkingoperation.infrastructure.ParkingLotOperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingLotOperationReadServiceImpl implements ParkingLotOperationReadService {

    private final ParkingLotOperationRepository parkingLotOperationRepository;

    @Async
    @Override
    public void create(OperationCreateEvent operationCreateEvent) {
        parkingLotOperationRepository.create(operationCreateEvent);
    }
}
