package com.parkmate.parkingservice.parkingspot.application;

import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotDeleteRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotGetRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotUpdateRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotResponseDto;
import com.parkmate.parkingservice.parkingspot.vo.request.ParkingSpotRegisterRequestVo;

import java.util.List;

public interface ParkingSpotService {

    void register(String parkingLotUuid,
                  ParkingSpotRegisterRequestVo parkingSpotRegisterRequestVo);

    void update(ParkingSpotUpdateRequestDto parkingSpotUpdateRequestDto);

    void delete(ParkingSpotDeleteRequestDto parkingSpotDeleteRequestDto);

    ParkingSpotResponseDto getParkingSpot(ParkingSpotGetRequestDto parkingSpotGetRequestDto);

    List<ParkingSpotResponseDto> getParkingSpots(String parkingLotUuid);
}
