package com.parkmate.parkingreadservice.geo.application;

import com.parkmate.parkingreadservice.geo.dto.request.GeoPointAddRequestDto;
import com.parkmate.parkingreadservice.geo.dto.request.InBoxParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.dto.request.NearbyParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.dto.request.UserParkingLotDistanceRequestDto;
import com.parkmate.parkingreadservice.geo.dto.response.GeoSearchResult;
import com.parkmate.parkingreadservice.geo.dto.response.UserParkingLotDistanceResponseDto;

import java.util.List;

public interface GeoService {

    void addParkingLot(GeoPointAddRequestDto geoPointAddRequestDto);

    void addParkingLot(String key,
                       List<GeoPointAddRequestDto> geoPointAddRequestsDto);

    List<GeoSearchResult> getParkingLotsNearby(NearbyParkingLotRequestDto nearbyParkingLotRequestDto);

    List<GeoSearchResult> getParkingLotsInBox(InBoxParkingLotRequestDto inBoxParkingLotRequestDto);

    UserParkingLotDistanceResponseDto getUserParkingLotDistance(UserParkingLotDistanceRequestDto userParkingLotDistanceRequestDto);
}
