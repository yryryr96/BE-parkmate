package com.parkmate.parking_service.parkinglot.presentation;

import com.parkmate.parking_service.parkinglot.application.ParkingLotService;
import com.parkmate.parking_service.parkinglot.dto.request.ParkingLotCreateRequestDto;
import com.parkmate.parking_service.parkinglot.vo.request.ParkingLotCreateRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parking-lots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @PostMapping("/register")
    public void registerParkingLot(@RequestBody ParkingLotCreateRequestVo parkingLotCreateRequestVo) {
        parkingLotService.register(ParkingLotCreateRequestDto.from(parkingLotCreateRequestVo));
    }
}
