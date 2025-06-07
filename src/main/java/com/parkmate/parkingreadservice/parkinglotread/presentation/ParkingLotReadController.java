package com.parkmate.parkingreadservice.parkinglotread.presentation;

import com.parkmate.parkingreadservice.common.response.ApiResponse;
import com.parkmate.parkingreadservice.parkinglotread.application.ParkingLotReadService;
import com.parkmate.parkingreadservice.parkinglotread.vo.response.ParkingLotReadResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parking-lot-read")
@RequiredArgsConstructor
public class ParkingLotReadController {

    private final ParkingLotReadService parkingLotReadService;

    @Operation(
            summary = "주차장 정보 조회",
            description = "주차장 UUID를 통해 주차장 정보를 조회하는 API입니다.",
            tags = {"PARKING-LOT-READ-SERVICE"}
    )
    @GetMapping("/{parkingLotUuid}")
    public ApiResponse<ParkingLotReadResponseVo> getParkingLotByParkingLotUuid(@PathVariable String parkingLotUuid) {

        return ApiResponse.ok(
                "주차장 정보 조회에 성공했습니다.",
                parkingLotReadService.getParkingLotReadByParkingLotUuid(parkingLotUuid).toVo()
        );
    }
}
