package com.parkmate.parkingservice.parkinglot.presentation;

import com.parkmate.parkingservice.parkinglot.application.ParkingLotService;
import com.parkmate.parkingservice.parkinglot.vo.response.ParkingLotHostUuidResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/parkingLots")
@RequiredArgsConstructor
public class InternalParkingLotController {

    private final ParkingLotService parkingLotService;

    @Operation(
            summary = "주차장 UUID로 호스트 UUID 조회",
            description = "주차장 UUID를 PathVariable로 받아 해당 주차장의 호스트 UUID를 조회하는 API입니다. " +
                    "FeignClient를 사용하여 다른 서비스와 통신합니다.",
            tags = {"PARKING-LOT-SERVICE"}
    )
    @GetMapping("/{parkingLotUuid}/host")
    public ParkingLotHostUuidResponseVo getHostUuidByParkingLotUuid(@PathVariable String parkingLotUuid) {
        return parkingLotService.getHostUuidByParkingLotUuid(parkingLotUuid).toVo();
    }
}
