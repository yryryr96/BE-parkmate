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

import java.time.LocalDateTime;
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
        return getGeoParkingLotResponseDtoList(nearbyParkingLots);
    }

    public GeoParkingLotResponseDtoList getParkingLotsInBox(InBoxParkingLotRequestDto inBoxParkingLotRequestDto) {

        List<GeoSearchResult> parkingLotsInBox = geoService.getParkingLotsInBox(inBoxParkingLotRequestDto);

        GeoParkingLotResponseDtoList geoResults = getGeoParkingLotResponseDtoList(parkingLotsInBox);
        List<GeoParkingLotResponseDto> geoParkingLots = geoResults.getParkingLots();

        LocalDateTime requestStart = LocalDateTime.now();
        LocalDateTime requestEnd = requestStart.plusHours(5);

        Set<String> operationResultSet = operationService.validateOperationByUuidAndDateRange(
                geoParkingLots.stream()
                        .map(GeoParkingLotResponseDto::getParkingLotUuid)
                        .toList(),
                requestStart,
                requestEnd
        );

        return GeoParkingLotResponseDtoList.from(geoParkingLots.stream()
                .filter(pr -> operationResultSet.contains(pr.getParkingLotUuid()))
                .toList());
    }

    private GeoParkingLotResponseDtoList getGeoParkingLotResponseDtoList(List<GeoSearchResult> geoSearchResults) {

        if (geoSearchResults == null || geoSearchResults.isEmpty()) {
            return GeoParkingLotResponseDtoList.from(Collections.emptyList());
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
            return GeoParkingLotResponseDtoList.from(new ArrayList<>(resultMap.values()));
        }

        List<GeoParkingLotResponseDto> nonCachedParkingLots = parkingLotReadService.getParkingLotsByUuids(nonCachedUuids);
        Map<String, GeoParkingLotResponseDto> nonCachedMap = nonCachedParkingLots.stream()
                .collect(Collectors.toMap(
                                GeoParkingLotResponseDto::getParkingLotUuid,
                                dto -> dto
                        )
                );

        resultMap.putAll(nonCachedMap);
        redisUtil.multiInsert(nonCachedMap);

        List<GeoParkingLotResponseDto> finalDetails = new ArrayList<>(resultMap.values());
        finalDetails.sort(Comparator.comparing(GeoParkingLotResponseDto::getDistance));

        return GeoParkingLotResponseDtoList.from(finalDetails);
    }
}
