package com.parkmate.parkingreadservice.facade;

import com.parkmate.parkingreadservice.common.utils.RedisUtil;
import com.parkmate.parkingreadservice.geo.application.GeoService;
import com.parkmate.parkingreadservice.geo.dto.request.InBoxParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.dto.request.NearbyParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.dto.response.InBoxParkingLotResponseDto;
import com.parkmate.parkingreadservice.geo.dto.response.InBoxParkingLotResponseDtoList;
import com.parkmate.parkingreadservice.geo.dto.response.GeoSearchResult;
import com.parkmate.parkingreadservice.parkinglotread.application.ParkingLotReadService;
import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotReadResponseDto;
import com.parkmate.parkingreadservice.parkingoperation.application.ParkingLotOperationReadService;
import com.parkmate.parkingreadservice.reservation.application.ReservationRecordService;
import com.parkmate.parkingreadservice.reservation.dto.response.ReserveParkingLotResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingLotFacade {

    private final ParkingLotReadService parkingLotReadService;
    private final ParkingLotOperationReadService operationService;
    private final ReservationRecordService reservationRecordService;
    private final GeoService geoService;
    private final RedisUtil<String, ParkingLotReadResponseDto> redisUtil;

    public InBoxParkingLotResponseDtoList getParkingLotsNearby(NearbyParkingLotRequestDto nearbyParkingLotRequestDto) {

        List<GeoSearchResult> nearbyParkingLots = geoService.getParkingLotsNearby(nearbyParkingLotRequestDto);
//        GeoParkingLotResponseDtoList.from(resolveParkingLotDetails(nearbyParkingLots));
        return null;
    }

    public InBoxParkingLotResponseDtoList getParkingLotsInBox(InBoxParkingLotRequestDto inBoxParkingLotRequestDto) {

        List<GeoSearchResult> parkingLotsInBox = geoService.getParkingLotsInBox(inBoxParkingLotRequestDto);
        List<ParkingLotReadResponseDto> parkingLotReadResponseDtos = resolveParkingLotDetails(parkingLotsInBox);

        LocalDateTime startDateTime = inBoxParkingLotRequestDto.getStartDateTime();
        LocalDateTime endDateTime = inBoxParkingLotRequestDto.getEndDateTime();

        List<ParkingLotReadResponseDto> filteredParkingLotsByOperation = filterByOperations(
                parkingLotReadResponseDtos,
                startDateTime,
                endDateTime
        );

        List<ParkingLotReadResponseDto> filteredParkingLotsByReservation = filterByReservationRecord(
                filteredParkingLotsByOperation,
                startDateTime,
                endDateTime
        );

        Map<String, GeoSearchResult> map = parkingLotsInBox.stream()
                .collect(Collectors.toMap(
                        GeoSearchResult::getParkingLotUuid,
                        geoSearchResult -> geoSearchResult
                ));

        List<InBoxParkingLotResponseDto> result = filteredParkingLotsByReservation.stream()
                .map(p -> {
                    GeoSearchResult geoSearchResult = map.get(p.getParkingLotUuid());
                    return InBoxParkingLotResponseDto.from(
                            p,
                            geoSearchResult.getLatitude(),
                            geoSearchResult.getLongitude(),
                            geoSearchResult.getDistance()
                    );
                })
                .toList();

        return InBoxParkingLotResponseDtoList.from(result);
    }

    private List<ParkingLotReadResponseDto> filterByReservationRecord(List<ParkingLotReadResponseDto> parkingLots,
                                                                      LocalDateTime startDateTime,
                                                                      LocalDateTime endDateTime) {

        Map<String, ParkingLotReadResponseDto> map = parkingLots.stream()
                .collect(Collectors.toMap(
                        ParkingLotReadResponseDto::getParkingLotUuid,
                        parkingLotReadResponseDto -> parkingLotReadResponseDto
                ));

        List<ReserveParkingLotResponseDto> reservedParkingLotInfos = reservationRecordService.getParkingLotUuidsByUuidsAndDates(
                parkingLots.stream()
                        .map(ParkingLotReadResponseDto::getParkingLotUuid)
                        .toList(),
                startDateTime,
                endDateTime
        );

        if (reservedParkingLotInfos == null || reservedParkingLotInfos.isEmpty()) {
            return parkingLots;
        }

        List<ParkingLotReadResponseDto> results = new ArrayList<>();
        for (ReserveParkingLotResponseDto reservedParkingLotInfo : reservedParkingLotInfos) {

            String parkingLotUuid = reservedParkingLotInfo.getParkingLotUuid();
            int reservedSpotCount = reservedParkingLotInfo.getReservedSpotCount();
            int availableSpotCount = map.get(parkingLotUuid).getCapacity() - reservedSpotCount;

            if (availableSpotCount > 0) {
                results.add(map.get(parkingLotUuid).changeCapacity(availableSpotCount));
            }
        }

        return results;
    }

    private List<ParkingLotReadResponseDto> filterByOperations(List<ParkingLotReadResponseDto> parkingLots,
                                                               LocalDateTime startDateTime,
                                                               LocalDateTime endDateTime) {

        Set<String> operationResultSet = operationService.findAvailableParkingLotUuids(
                parkingLots.stream()
                        .map(ParkingLotReadResponseDto::getParkingLotUuid)
                        .toList(),
                startDateTime,
                endDateTime
        );

        return parkingLots.stream()
                .filter(pr -> operationResultSet.contains(pr.getParkingLotUuid()))
                .toList();
    }

    private List<ParkingLotReadResponseDto> resolveParkingLotDetails(List<GeoSearchResult> geoSearchResults) {

        if (geoSearchResults == null || geoSearchResults.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> allUuids = geoSearchResults.stream()
                .map(GeoSearchResult::getParkingLotUuid)
                .toList();

        List<ParkingLotReadResponseDto> cachedParkingLots = redisUtil.nullableMultiSelect(allUuids);
        Map<String, ParkingLotReadResponseDto> resultMap = new HashMap<>();
        List<String> nonCachedUuids = new ArrayList<>();

        for (int idx = 0; idx < allUuids.size(); idx++) {
            ParkingLotReadResponseDto dto = cachedParkingLots.get(idx);
            if (dto != null) {
                resultMap.put(allUuids.get(idx),
                        dto);
            } else {
                nonCachedUuids.add(allUuids.get(idx));
            }
        }

        if (nonCachedUuids.isEmpty()) {
            return new ArrayList<>(resultMap.values());
        }

        List<ParkingLotReadResponseDto> nonCachedParkingLots = parkingLotReadService.getParkingLotsByUuids(nonCachedUuids);
        Map<String, ParkingLotReadResponseDto> nonCachedMap = nonCachedParkingLots.stream()
                .collect(Collectors.toMap(
                                ParkingLotReadResponseDto::getParkingLotUuid,
                                parkingLotReadResponseDto -> parkingLotReadResponseDto
                        )
                );

        resultMap.putAll(nonCachedMap);
        redisUtil.multiInsert(nonCachedMap);

        return new ArrayList<>(resultMap.values());
    }
}
