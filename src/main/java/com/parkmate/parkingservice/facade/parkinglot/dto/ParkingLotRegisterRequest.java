package com.parkmate.parkingservice.facade.parkinglot.dto;

import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotRegisterRequestDto;
import com.parkmate.parkingservice.parkinglotimagemapping.vo.request.ParkingLotImageRegisterRequestVo;
import com.parkmate.parkingservice.parkingspot.vo.request.ParkingSpotRegisterRequestVo;
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
    private ParkingSpotRegisterRequestVo parkingSpot;
    private ParkingLotImageRegisterRequestVo parkingLotImage;
}
