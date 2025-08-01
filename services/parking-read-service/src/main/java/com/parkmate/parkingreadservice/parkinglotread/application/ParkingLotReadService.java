package com.parkmate.parkingreadservice.parkinglotread.application;

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

import java.util.List;

public interface ParkingLotReadService {

    void createParkingLot(ParkingLotCreateEvent parkingLotCreateEvent);

    ParkingLotReadResponseDto getParkingLotReadByParkingLotUuid(String parkingLotUuid);

    void syncParkingLotMetadata(ParkingLotMetadataUpdateEvent parkingLotMetadataUpdateEvent);

    void syncParkingLotReactions(List<ParkingLotReactionsUpdateEvent> parkingLotReactionsUpdateEventList);

    List<ParkingLotReadResponseDto> getParkingLotsByUuids(List<String> parkingLotUuids);

    List<ParkingLotRead> findAll();

    ParkingLotReadSimpleResponseDto getParkingLotReadSimpleByParkingLotUuid(String parkingLotUuid);

    void bulkUpdateReviewInfo(List<ReviewSummaryUpdateEvent> events);

    CursorPage<ParkingLotSearchResponseDto> search(ParkingLotSearchRequestDto parkingLotSearchRequestDto);
}
