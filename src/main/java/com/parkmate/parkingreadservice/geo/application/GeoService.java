package com.parkmate.parkingreadservice.geo.application;

import com.parkmate.parkingreadservice.geo.dto.response.ParkingLotsInRadiusResponse;

import java.util.List;

public interface GeoService {

    void addParkingLot(String parkingLotUuid, double latitude, double longitude);

    List<ParkingLotsInRadiusResponse> getNearbyParkingLots(double latitude, double longitude, double radius);

    List<ParkingLotsInRadiusResponse> getParkingLotsInBox();


}
