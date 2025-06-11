package com.parkmate.parkingreadservice.kafka.eventmanager;

import com.parkmate.parkingreadservice.geo.application.GeoService;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.kafka.event.ParkingLotReactionsUpdateEvent;
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
        geoService.addParkingLot(event.getParkingLotUuid(), event.getLatitude(), event.getLongitude());
    }

    public void handleParkingLotMetaDataUpdatedEvent(ParkingLotMetadataUpdateEvent event) {
        parkingLotReadService.syncParkingLotMetadata(event);
    }

    public void handleParkingLotReactionsUpdatedEvent(List<ParkingLotReactionsUpdateEvent> events) {
        parkingLotReadService.syncParkingLotReactions(events);
    }
}
