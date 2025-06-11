package com.parkmate.parkingservice.facade.dto;

import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotRegisterRequestDto;
import com.parkmate.parkingservice.parkinglotimagemapping.dto.request.ParkingLotImageRegisterRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotRegisterRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotRegisterRequest {

    private ParkingLotRegisterRequestDto parkingLot;
    private List<Long> optionIds;
    private ParkingSpotRegisterRequestDto parkingSpot;
    private ParkingLotImageRegisterRequestDto parkingLotImage;
}
