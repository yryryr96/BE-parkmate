package com.parkmate.parkingreadservice.parkingoperation.application;

import com.parkmate.parkingreadservice.kafka.event.OperationCreateEvent;
import com.parkmate.parkingreadservice.parkingoperation.dto.response.ParkingLotOperationResponseDto;
import com.parkmate.parkingreadservice.parkingoperation.infrastructure.ParkingLotOperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotOperationReadServiceImpl implements ParkingLotOperationReadService {

    private final ParkingLotOperationRepository parkingLotOperationRepository;

    @Async
    @Override
    public void create(OperationCreateEvent operationCreateEvent) {
        parkingLotOperationRepository.save(operationCreateEvent.toEntity());
    }

    @Override
    public List<ParkingLotOperationResponseDto> getOperationsByUuidAndDateRange(List<String> parkingLotUuids,
                                                                                LocalDateTime startDateTime,
                                                                                LocalDateTime endDateTime) {
        parkingLotOperationRepository.findAllByUuidAndOperationDateBetween(
                parkingLotUuids,
                startDateTime,
                endDateTime
        );

        return null;
    }
}
