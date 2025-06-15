package com.parkmate.parkingservice.parkingspot.application;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotDeleteRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotGetRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotRegisterRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotUpdateRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotResponseDto;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotSimpleResponseDto;

import java.util.List;

public interface ParkingSpotService {

    List<ParkingSpotResponseDto> register(ParkingSpotRegisterRequestDto parkingSpotRegisterRequestDto);

    void update(ParkingSpotUpdateRequestDto parkingSpotUpdateRequestDto);

    void delete(ParkingSpotDeleteRequestDto parkingSpotDeleteRequestDto);

    ParkingSpotResponseDto getParkingSpot(ParkingSpotGetRequestDto parkingSpotGetRequestDto);

    List<ParkingSpotResponseDto> getParkingSpots(String parkingLotUuid);

    List<ParkingSpotSimpleResponseDto> getParkingSpotsByParkingLotUuidAndType(String parkingLotUuid,
                                                                              ParkingSpotType parkingSpotType);
}
