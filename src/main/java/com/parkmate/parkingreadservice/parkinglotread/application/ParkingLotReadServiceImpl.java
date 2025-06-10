package com.parkmate.parkingreadservice.parkinglotread.application;

import com.parkmate.parkingreadservice.common.exception.BaseException;
import com.parkmate.parkingreadservice.common.response.ResponseStatus;
import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotReadResponseDto;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotReactionsUpdateEvent;
import com.parkmate.parkingreadservice.parkinglotread.infrastructure.ParkingLotReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingLotReadServiceImpl implements ParkingLotReadService {

    private final ParkingLotReadRepository parkingLotReadRepository;

    @Async
    @Override
    public void createParkingLot(ParkingLotCreateEvent parkingLotCreateEvent) {
        parkingLotReadRepository.save(parkingLotCreateEvent.toEntity());
    }

    @Async
    @Override
    public void syncParkingLotMetadata(ParkingLotMetadataUpdateEvent parkingLotMetadataUpdateEvent) {
        parkingLotReadRepository.updateParkingLotMetadata(parkingLotMetadataUpdateEvent);
    }

    @Async
    @Override
    public void syncParkingLotReactions(List<ParkingLotReactionsUpdateEvent> parkingLotReactionsUpdateEvents) {
        parkingLotReadRepository.updateParkingLotReactions(parkingLotReactionsUpdateEvents);
    }

    @Override
    public ParkingLotReadResponseDto getParkingLotReadByParkingLotUuid(String parkingLotUuid) {

        ParkingLotRead parkingLotRead = parkingLotReadRepository.findByParkingLotUuid(parkingLotUuid)
                .orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        return ParkingLotReadResponseDto.from(parkingLotRead);
    }
}
