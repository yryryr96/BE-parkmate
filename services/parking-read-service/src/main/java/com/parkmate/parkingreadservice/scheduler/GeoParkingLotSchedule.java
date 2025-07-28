package com.parkmate.parkingreadservice.scheduler;

import com.parkmate.parkingreadservice.common.utils.RedisUtil;
import com.parkmate.parkingreadservice.geo.application.GeoService;
import com.parkmate.parkingreadservice.geo.dto.request.GeoPointAddRequestDto;
import com.parkmate.parkingreadservice.parkinglotread.application.ParkingLotReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GeoParkingLotSchedule {

    private final GeoService geoService;
    private final ParkingLotReadService parkingLotReadService;
    private final RedisUtil<String, String> redisUtil;

    @Scheduled(fixedDelay = 60000)
    public void syncGeoParkingLots() {

        final String GEO_KEY = "geopoints";
        final String NEXT_GEO_KEY = "next_geopoints";

        List<GeoPointAddRequestDto> parkingLots = parkingLotReadService.findAll().stream()
                .map(parkingLot -> GeoPointAddRequestDto.builder()
                        .parkingLotUuid(parkingLot.getParkingLotUuid())
                        .latitude(parkingLot.getLatitude())
                        .longitude(parkingLot.getLongitude())
                        .build())
                .toList();

        log.info("sync 작업 시작: {}개 주차장", parkingLots.size());

        redisUtil.delete(NEXT_GEO_KEY);
        geoService.addParkingLot(NEXT_GEO_KEY, parkingLots);

        redisUtil.rename(NEXT_GEO_KEY, GEO_KEY);

    }
}
