package com.parkmate.parkingreadservice.parkinglotread.application;

import com.parkmate.parkingreadservice.common.exception.BaseException;
import com.parkmate.parkingreadservice.common.exception.ResponseStatus;
import com.parkmate.parkingreadservice.common.response.CursorPage;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ParkingLotReactionsUpdateEvent;
import com.parkmate.parkingreadservice.kafka.event.review.ReviewSummaryUpdateEvent;
import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import com.parkmate.parkingreadservice.parkinglotread.dto.request.ParkingLotSearchRequestDto;
import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotReadResponseDto;
import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotReadSimpleResponseDto;
import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotSearchResponseDto;
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
        parkingLotReadRepository.create(parkingLotCreateEvent);
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

    @Override
    public List<ParkingLotReadResponseDto> getParkingLotsByUuids(List<String> parkingLotUuids) {
        return parkingLotReadRepository.findByParkingLotUuids(parkingLotUuids)
                .stream()
                .map(ParkingLotReadResponseDto::from)
                .toList();
    }

    @Override
    public List<ParkingLotRead> findAll() {
        return parkingLotReadRepository.findAll();
    }

    @Override
    public ParkingLotReadSimpleResponseDto getParkingLotReadSimpleByParkingLotUuid(String parkingLotUuid) {
        return ParkingLotReadSimpleResponseDto.from(
                parkingLotReadRepository.findByParkingLotUuid(parkingLotUuid)
                        .orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND)
                        ));
    }

    @Override
    public void bulkUpdateRating(List<ReviewSummaryUpdateEvent> events) {
        parkingLotReadRepository.bulkUpdateRating(events);
    }

    @Override
    public CursorPage<ParkingLotSearchResponseDto> search(ParkingLotSearchRequestDto parkingLotSearchRequestDto) {

        return parkingLotReadRepository.search(parkingLotSearchRequestDto)
                .map(ParkingLotSearchResponseDto::from);
    }
}
