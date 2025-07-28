package com.parkmate.parkingreadservice.kafka.eventmanager;

import com.parkmate.parkingreadservice.geo.application.GeoService;
import com.parkmate.parkingreadservice.geo.dto.request.GeoPointAddRequestDto;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ParkingLotReactionsUpdateEvent;
import com.parkmate.parkingreadservice.parkinglotread.application.ParkingLotReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotEventManager {

    private final ParkingLotReadService parkingLotReadService;
    private final GeoService geoService;

    public void handleParkingLotCreatedEvent(ParkingLotCreateEvent event) {

        parkingLotReadService.createParkingLot(event);
        geoService.addParkingLot(
                GeoPointAddRequestDto.builder()
                        .parkingLotUuid(event.getParkingLotUuid())
                        .latitude(event.getLatitude())
                        .longitude(event.getLongitude())
                        .build()
        );
    }

    public void handleParkingLotMetadataUpdatedEvent(ParkingLotMetadataUpdateEvent event) {
        parkingLotReadService.syncParkingLotMetadata(event);
    }

    public void handleParkingLotReactionsUpdatedEvent(List<ParkingLotReactionsUpdateEvent> events) {
        parkingLotReadService.syncParkingLotReactions(events);
    }
}
