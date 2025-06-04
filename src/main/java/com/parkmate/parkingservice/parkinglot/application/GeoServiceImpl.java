package com.parkmate.parkingservice.parkinglot.application;

import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLotGeoLocation;
import com.parkmate.parkingservice.parkinglot.dto.response.ParkingLotGeoResponseDto;
import com.parkmate.parkingservice.parkinglot.infrastructure.ParkingLotGeoLocationRepository;
import com.parkmate.parkingservice.parkinglot.infrastructure.ParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoServiceImpl implements GeoService {

    private final GeoOperations<String, String> geoOperations;
    private final ParkingLotRepository parkingLotRepository;

    private static final String GEO_KEY = "geopoints";
    private static final int LIMIT_PER_RESPONSE = 10;
    private final ParkingLotGeoLocationRepository parkingLotGeoLocationRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        List<ParkingLot> parkinglots = parkingLotRepository.findAll();
//        parkinglots.forEach(parkingLot -> {
//            geoOperations.add(GEO_KEY,
//                    new Point(parkingLot.getLongitude(), parkingLot.getLatitude()),
//                    parkingLot.getParkingLotUuid()
//            );
//        });

        parkinglots.forEach(parkingLotGeoLocationRepository::insert);
    }

    @Override
    public void addParkingLot(String parkingLotUuid,
                              double latitude,
                              double longitude) {

        geoOperations.add(GEO_KEY, new Point(longitude, latitude), parkingLotUuid);
    }

    @Override
    public List<ParkingLotGeoResponseDto> getNearbyParkingLots(double latitude,
                                                               double longitude,
                                                               double radius) {

        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeCoordinates()  // 좌표 포함
                .includeDistance()     // 거리 포함
                .sortAscending()       // 오름차순 정렬 (가까운 거리부터)
                .limit(LIMIT_PER_RESPONSE);    // 최대 결과 수 제한

        Point point = new Point(longitude, latitude);
        Distance distance = new Distance(radius, RedisGeoCommands.DistanceUnit.KILOMETERS);
        Circle searchArea = new Circle(point, distance);

        GeoResults<RedisGeoCommands.GeoLocation<String>> result = geoOperations.radius(GEO_KEY, searchArea, args);
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> content = result.getContent();

        return content.stream()
                .map(gr -> {
                    return ParkingLotGeoResponseDto.builder()
                            .parkingLotUuid(gr.getContent().getName())
                            .latitude(gr.getContent().getPoint().getY())
                            .longitude(gr.getContent().getPoint().getX())
                            .distance(gr.getDistance().getValue())
                            .build();
                })
                .toList();
    }
}
