package com.parkmate.parkingreadservice.geo.application;

import com.parkmate.parkingreadservice.geo.dto.response.ParkingLotsInRadiusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoServiceImplByRedis implements GeoService {

    private final GeoOperations<String, String> geoOperations;

    private static final String GEO_KEY = "geopoints";
    private static final int LIMIT_PER_RESPONSE = 10;
    private static final double DISTANCE_PRECISION_FACTOR = 10.0;

    @Override
    public void addParkingLot(String parkingLotUuid,
                              double latitude,
                              double longitude) {

        geoOperations.add(GEO_KEY, new Point(longitude, latitude), parkingLotUuid);
    }

    @Override
    public List<ParkingLotsInRadiusResponse> getNearbyParkingLots(double latitude,
                                                                  double longitude,
                                                                  double radius) {

        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeCoordinates()
                .includeDistance()
                .sortAscending()
                .limit(LIMIT_PER_RESPONSE);

        Point point = new Point(longitude, latitude);
        Distance distance = new Distance(radius, RedisGeoCommands.DistanceUnit.KILOMETERS);
        Circle searchArea = new Circle(point, distance);

        GeoResults<RedisGeoCommands.GeoLocation<String>> result = geoOperations.radius(GEO_KEY, searchArea, args);
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> content = result == null ? Collections.emptyList() : result.getContent();

        return content.stream()
                .map(gr -> {
                    double dist = Math.round(gr.getDistance().getValue() * DISTANCE_PRECISION_FACTOR) / DISTANCE_PRECISION_FACTOR;
                    return ParkingLotsInRadiusResponse.builder()
                            .parkingLotUuid(gr.getContent().getName())
                            .latitude(gr.getContent().getPoint().getY())
                            .longitude(gr.getContent().getPoint().getX())
                            .distance(dist)
                            .build();
                })
                .toList();
    }
}
