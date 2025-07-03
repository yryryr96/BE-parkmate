package com.parkmate.parkingreadservice.geo.application;

import com.parkmate.parkingreadservice.common.exception.BaseException;
import com.parkmate.parkingreadservice.common.response.ResponseStatus;
import com.parkmate.parkingreadservice.geo.dto.request.GeoPointAddRequestDto;
import com.parkmate.parkingreadservice.geo.dto.request.InBoxParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.dto.request.NearbyParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.dto.request.UserParkingLotDistanceRequestDto;
import com.parkmate.parkingreadservice.geo.dto.response.GeoSearchResult;
import com.parkmate.parkingreadservice.geo.dto.response.UserParkingLotDistanceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.domain.geo.BoundingBox;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeoServiceImplByRedis implements GeoService {

    private final GeoOperations<String, String> geoOperations;

    private static final String GEO_KEY = "geopoints";
    private static final int LIMIT_PER_RESPONSE = 10;
    private static final double DISTANCE_PRECISION_FACTOR = 10.0;

    @Override
    public void addParkingLot(GeoPointAddRequestDto geoPointAddRequestDto) {

        String parkingLotUuid = geoPointAddRequestDto.getParkingLotUuid();
        double latitude = geoPointAddRequestDto.getLatitude();
        double longitude = geoPointAddRequestDto.getLongitude();

        geoOperations.add(GEO_KEY, new Point(longitude, latitude), parkingLotUuid);
    }

    @Override
    public void addParkingLot(String key,
                              List<GeoPointAddRequestDto> geoPointAddRequestsDto) {

        Map<String, Point> locationMap = geoPointAddRequestsDto.stream()
                .collect(Collectors.toMap(
                        GeoPointAddRequestDto::getParkingLotUuid,
                        dto -> new Point(dto.getLongitude(), dto.getLatitude())
                ));

        geoOperations.add(key, locationMap);
    }

    @Override
    public List<GeoSearchResult> getParkingLotsNearby(NearbyParkingLotRequestDto nearbyParkingLotRequestDto) {

        double latitude = nearbyParkingLotRequestDto.getLatitude();
        double longitude = nearbyParkingLotRequestDto.getLongitude();
        double radius = nearbyParkingLotRequestDto.getRadius();

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
                    double dist = changeDistanceFormat(gr.getDistance().getValue());
                    return GeoSearchResult.builder()
                            .parkingLotUuid(gr.getContent().getName())
                            .latitude(gr.getContent().getPoint().getY())
                            .longitude(gr.getContent().getPoint().getX())
                            .distance(dist)
                            .build();
                })
                .toList();
    }

    @Override
    public List<GeoSearchResult> getParkingLotsInBox(InBoxParkingLotRequestDto inBoxParkingLotRequestDto) {

        double swLat = inBoxParkingLotRequestDto.getSwLat();
        double swLng = inBoxParkingLotRequestDto.getSwLng();
        double neLat = inBoxParkingLotRequestDto.getNeLat();
        double neLng = inBoxParkingLotRequestDto.getNeLng();
        Point bottomLeft = new Point(swLng, swLat);
        Point topRight = new Point(neLng, neLat);

        Distance width = new Distance(calculateWidth(bottomLeft, topRight), Metrics.KILOMETERS);
        Distance height = new Distance(calculateHeight(bottomLeft, topRight), Metrics.KILOMETERS);
        BoundingBox boundingBox = new BoundingBox(width, height);

        double centerLat = (swLat + neLat) / 2;
        double centerLng = (swLng + neLng) / 2;
        Point center = new Point(centerLng, centerLat);
        GeoReference<String> reference = GeoReference.fromCoordinate(center);

        RedisGeoCommands.GeoSearchCommandArgs args = RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs()
                .includeCoordinates()
                .includeDistance()
                .sortAscending();

        GeoResults<RedisGeoCommands.GeoLocation<String>> result = geoOperations.search(
                GEO_KEY,
                reference,
                boundingBox,
                args
        );

        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> content = result == null ? Collections.emptyList() : result.getContent();
        return content.stream()
                .map(gr -> {
                    double dist = changeDistanceFormat(gr.getDistance().getValue());
                    return GeoSearchResult.builder()
                            .parkingLotUuid(gr.getContent().getName())
                            .latitude(gr.getContent().getPoint().getY())
                            .longitude(gr.getContent().getPoint().getX())
                            .distance(dist)
                            .build();
                })
                .toList();
    }

    @Override
    public UserParkingLotDistanceResponseDto getUserParkingLotDistance(UserParkingLotDistanceRequestDto userParkingLotDistanceRequestDto) {

        List<Point> points = geoOperations.position(GEO_KEY, userParkingLotDistanceRequestDto.getParkingLotUuid());

        if (points == null || points.isEmpty() || points.get(0) == null) {
            throw new BaseException(ResponseStatus.RESOURCE_NOT_FOUND);
        }

        Point parkingLotPoint = points.get(0);

        Point userPoint = new Point(
                userParkingLotDistanceRequestDto.getLongitude(),
                userParkingLotDistanceRequestDto.getLatitude()
        );

        double distanceInKm = changeDistanceFormat(calculateHaversineDistance(userPoint, parkingLotPoint));
        return UserParkingLotDistanceResponseDto.builder()
                .distance(distanceInKm)
                .build();
    }

    private double calculateWidth(Point bottomLeft, Point topRight) {

        Point topLeft = new Point(bottomLeft.getX(), topRight.getY());
        return calculateHaversineDistance(topLeft, topRight);
    }

    private double calculateHeight(Point bottomLeft, Point topRight) {

        Point bottomRight = new Point(topRight.getX(), bottomLeft.getY());
        return calculateHaversineDistance(topRight, bottomRight);
    }

    private double calculateHaversineDistance(Point p1,
                                              Point p2) {

        final int R = 6371;
        double latDistance = Math.toRadians(p2.getY() - p1.getY());
        double lonDistance = Math.toRadians(p2.getX() - p1.getX());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                   + Math.cos(Math.toRadians(p1.getY())) * Math.cos(Math.toRadians(p2.getY()))
                     * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    private double changeDistanceFormat(double distance) {
        return Math.round(distance * DISTANCE_PRECISION_FACTOR) / DISTANCE_PRECISION_FACTOR;
    }
}
