package com.parkmate.parkingreadservice.facade;

import com.parkmate.parkingreadservice.common.utils.RedisUtil;
import com.parkmate.parkingreadservice.geo.application.GeoService;
import com.parkmate.parkingreadservice.geo.dto.request.InBoxParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.dto.request.NearbyParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.dto.response.GeoParkingLotResponseDto;
import com.parkmate.parkingreadservice.geo.dto.response.GeoParkingLotResponseDtoList;
import com.parkmate.parkingreadservice.geo.dto.response.GeoSearchResult;
import com.parkmate.parkingreadservice.parkinglotread.application.ParkingLotReadService;
import com.parkmate.parkingreadservice.parkingoperation.application.ParkingLotOperationReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingLotFacade {

    private final ParkingLotReadService parkingLotReadService;
    private final GeoService geoService;
    private final ParkingLotOperationReadService operationService;
    private final RedisUtil<String, GeoParkingLotResponseDto> redisUtil;

    public GeoParkingLotResponseDtoList getParkingLotsNearby(NearbyParkingLotRequestDto nearbyParkingLotRequestDto) {

        List<GeoSearchResult> nearbyParkingLots = geoService.getParkingLotsNearby(nearbyParkingLotRequestDto);
        return GeoParkingLotResponseDtoList.from(resolveParkingLotDetails(nearbyParkingLots));
    }

    public GeoParkingLotResponseDtoList getParkingLotsInBox(InBoxParkingLotRequestDto inBoxParkingLotRequestDto) {

        List<GeoSearchResult> parkingLotsInBox = geoService.getParkingLotsInBox(inBoxParkingLotRequestDto);
        List<GeoParkingLotResponseDto> geoParkingLots = resolveParkingLotDetails(parkingLotsInBox);

        List<GeoParkingLotResponseDto> availableParkingLots = filterByOperations(inBoxParkingLotRequestDto, geoParkingLots);

        return GeoParkingLotResponseDtoList.from(availableParkingLots);
    }

    private List<GeoParkingLotResponseDto> filterByOperations(InBoxParkingLotRequestDto inBoxParkingLotRequestDto,
                                                                        List<GeoParkingLotResponseDto> geoParkingLots) {

        Set<String> operationResultSet = operationService.findAvailableParkingLotUuids(
                geoParkingLots.stream()
                        .map(GeoParkingLotResponseDto::getParkingLotUuid)
                        .toList(),
                inBoxParkingLotRequestDto.getStartDate(),
                inBoxParkingLotRequestDto.getEndDate()
        );

        return geoParkingLots.stream()
                .filter(pr -> operationResultSet.contains(pr.getParkingLotUuid()))
                .toList();
    }

    private List<GeoParkingLotResponseDto> resolveParkingLotDetails(List<GeoSearchResult> geoSearchResults) {

        if (geoSearchResults == null || geoSearchResults.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> allUuids = geoSearchResults.stream()
                .map(GeoSearchResult::getParkingLotUuid)
                .toList();

        List<GeoParkingLotResponseDto> cachedParkingLots = redisUtil.nullableMultiSelect(allUuids);
        Map<String, GeoParkingLotResponseDto> resultMap = new HashMap<>();
        List<String> nonCachedUuids = new ArrayList<>();

        for (int idx = 0; idx < allUuids.size(); idx++) {
            GeoParkingLotResponseDto dto = cachedParkingLots.get(idx);
            if (dto != null) {
                resultMap.put(allUuids.get(idx), dto);
            } else {
                nonCachedUuids.add(allUuids.get(idx));
            }
        }

        if (nonCachedUuids.isEmpty()) {
            return new ArrayList<>(resultMap.values());
        }

        Map<String, Double> distanceMap = geoSearchResults.stream()
                .filter(result -> nonCachedUuids.contains(result.getParkingLotUuid()))
                .collect(Collectors.toMap(
                        GeoSearchResult::getParkingLotUuid,
                        GeoSearchResult::getDistance
                ));

        List<GeoParkingLotResponseDto> nonCachedParkingLots = parkingLotReadService.getParkingLotsByUuids(nonCachedUuids);
        Map<String, GeoParkingLotResponseDto> nonCachedMap = nonCachedParkingLots.stream()
                .collect(Collectors.toMap(
                                GeoParkingLotResponseDto::getParkingLotUuid,
                                dto -> dto.withDistance(distanceMap.get(dto.getParkingLotUuid()))
                        )
                );

        resultMap.putAll(nonCachedMap);
        redisUtil.multiInsert(nonCachedMap);

        List<GeoParkingLotResponseDto> finalDetails = new ArrayList<>(resultMap.values());
        finalDetails.sort(Comparator.comparing(GeoParkingLotResponseDto::getDistance));
        return finalDetails;
    }
}
