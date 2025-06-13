package com.parkmate.parkingreadservice.parkinglotread.presentation;

import com.parkmate.parkingreadservice.common.response.ApiResponse;
import com.parkmate.parkingreadservice.facade.ParkingLotFacade;
import com.parkmate.parkingreadservice.geo.dto.request.InBoxParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.dto.request.NearbyParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.vo.request.InBoxParkingLotRequestVo;
import com.parkmate.parkingreadservice.geo.vo.response.GeoParkingLotResponseVoList;
import com.parkmate.parkingreadservice.parkinglotread.application.ParkingLotReadService;
import com.parkmate.parkingreadservice.parkinglotread.vo.response.ParkingLotReadResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parkingLots")
@RequiredArgsConstructor
public class ParkingLotReadController {

    private final ParkingLotReadService parkingLotReadService;
    private final ParkingLotFacade parkingLotFacade;

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

    @GetMapping("/nearby")
    public ApiResponse<GeoParkingLotResponseVoList> getNearbyParkingLots(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double radius) {

        return ApiResponse.ok(
                "주변 주차장 정보 조회에 성공했습니다.",
                parkingLotFacade.getParkingLotsNearby(NearbyParkingLotRequestDto.of(latitude, longitude, radius)).toVo()
        );
    }

    @GetMapping("/box")
    public ApiResponse<GeoParkingLotResponseVoList> getParkingLotInBox(
            @ModelAttribute InBoxParkingLotRequestVo inBoxParkingLotRequestVo) {
        return ApiResponse.ok(
                "주차장 박스 정보 조회에 성공했습니다.",
                parkingLotFacade.getParkingLotsInBox(InBoxParkingLotRequestDto.of(inBoxParkingLotRequestVo)).toVo()
        );
    }
}
