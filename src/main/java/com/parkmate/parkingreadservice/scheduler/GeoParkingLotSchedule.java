package com.parkmate.parkingreadservice.scheduler;

import com.parkmate.parkingreadservice.geo.application.GeoService;
import com.parkmate.parkingreadservice.geo.dto.request.GeoPointAddRequestDto;
import com.parkmate.parkingreadservice.parkinglotread.application.ParkingLotReadService;
import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GeoParkingLotSchedule {

    private final GeoService geoService;
    private final ParkingLotReadService parkingLotReadService;

    @Scheduled(fixedDelay = 60 * 10 * 10)
    public void syncGeoParkingLots() {

        int sequence = 0;
        int limit = 20;

        while (true) {

            List<ParkingLotRead> parkingLots = parkingLotReadService.findAllBySequenceAndLimit(sequence, limit);
            parkingLots.forEach(parkingLot -> {

                GeoPointAddRequestDto request = GeoPointAddRequestDto.builder()
                        .parkingLotUuid(parkingLot.getParkingLotUuid())
                        .latitude(parkingLot.getLatitude())
                        .longitude(parkingLot.getLongitude())
                        .build();

                geoService.addParkingLot(request);
            });

            if (parkingLots.size() < limit) {
                return;
            }
        }
    }
}
