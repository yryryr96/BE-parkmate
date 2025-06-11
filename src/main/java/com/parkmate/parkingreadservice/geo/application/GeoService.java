package com.parkmate.parkingreadservice.geo.application;

import com.parkmate.parkingreadservice.geo.dto.request.InBoxParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.dto.request.NearbyParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.dto.response.GeoSearchResult;

import java.util.List;

public interface GeoService {

    void addParkingLot(String parkingLotUuid, double latitude, double longitude);

    List<GeoSearchResult> getParkingLotsNearby(NearbyParkingLotRequestDto nearbyParkingLotRequestDto);

    List<GeoSearchResult> getParkingLotsInBox(InBoxParkingLotRequestDto inBoxParkingLotRequestDto);
}
