package com.parkmate.parkingreadservice.parkinglotread.infrastructure;

import com.parkmate.parkingreadservice.common.response.CursorPage;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ParkingLotReactionsUpdateEvent;
import com.parkmate.parkingreadservice.kafka.event.review.ReviewSummaryUpdateEvent;
import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import com.parkmate.parkingreadservice.parkinglotread.dto.request.ParkingLotSearchRequestDto;
import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotSearchDto;

import java.util.List;

public interface CustomParkingLotRepository {

    void create(ParkingLotCreateEvent parkingLotCreateEvent);

    void updateParkingLotMetadata(ParkingLotMetadataUpdateEvent parkingLotMetadataUpdateEvent);

    void updateParkingLotReactions(List<ParkingLotReactionsUpdateEvent> parkingLotReactionsUpdateEvents);

    List<ParkingLotRead> findByParkingLotUuids(List<String> parkingLotUuids);

    void bulkUpdateRating(List<ReviewSummaryUpdateEvent> events);

    CursorPage<ParkingLotSearchDto> search(ParkingLotSearchRequestDto request);
}
