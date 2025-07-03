package com.parkmate.parkingreadservice.parkinglotread.presentation;

import com.parkmate.parkingreadservice.common.response.ApiResponse;
import com.parkmate.parkingreadservice.facade.ParkingLotFacade;
import com.parkmate.parkingreadservice.geo.dto.request.InBoxParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.dto.request.NearbyParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.dto.request.UserParkingLotDistanceRequestDto;
import com.parkmate.parkingreadservice.geo.vo.request.InBoxParkingLotRequestVo;
import com.parkmate.parkingreadservice.geo.vo.response.InBoxParkingLotResponseList;
import com.parkmate.parkingreadservice.geo.vo.response.NearbyParkingLotResponseVoList;
import com.parkmate.parkingreadservice.geo.vo.response.UserParkingLotDistanceResponseVo;
import com.parkmate.parkingreadservice.parkinglotread.application.ParkingLotReadService;
import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotSearchResponseDto;
import com.parkmate.parkingreadservice.parkinglotread.vo.response.ParkingLotReadResponseVo;
import com.parkmate.parkingreadservice.parkinglotread.vo.response.ParkingLotReadSimpleResponseVo;
import com.parkmate.parkingreadservice.parkinglotread.vo.response.ParkingLotSearchResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(
            summary = "주차장 간단 정보 조회",
            description = "주차장 UUID를 통해 주차장의 간단한 정보를 조회하는 API입니다.",
            tags = {"PARKING-LOT-READ-SERVICE"}
    )
    @GetMapping("/{parkingLotUuid}/simple")
    public ApiResponse<ParkingLotReadSimpleResponseVo> getSimpleParkingLotByParkingLotUuid(@PathVariable String parkingLotUuid) {

        return ApiResponse.ok(
                "주차장 간단 정보 조회에 성공했습니다.",
                parkingLotReadService.getParkingLotReadSimpleByParkingLotUuid(parkingLotUuid).toVo()
        );
    }

    @Operation(
            summary = "주차장 UUID 목록으로 주변 주차장 정보 조회",
            description = "주차장 UUID 목록을 통해 주변 주차장 정보를 조회하는 API입니다.",
            tags = {"PARKING-LOT-READ-SERVICE"}
    )
    @GetMapping("/nearby")
    public ApiResponse<NearbyParkingLotResponseVoList> getNearbyParkingLots(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double radius) {

        return ApiResponse.ok(
                "주변 주차장 정보 조회에 성공했습니다.",
                parkingLotFacade.getParkingLotsNearby(NearbyParkingLotRequestDto.of(latitude, longitude, radius)).toVo()
        );
    }

    @Operation(
            summary = "주차장 박스 정보 조회",
            description = "박스 형태로 주차장 정보를 조회하는 API입니다. " +
                    "박스의 모서리 좌표를 통해 해당 영역 내의 주차장 정보를 조회합니다.",
            tags = {"PARKING-LOT-READ-SERVICE"}
    )
    @GetMapping("/box")
    public ApiResponse<InBoxParkingLotResponseList> getParkingLotInBox(
            @ModelAttribute InBoxParkingLotRequestVo inBoxParkingLotRequestVo) {
        return ApiResponse.ok(
                "주차장 박스 정보 조회에 성공했습니다.",
                parkingLotFacade.getParkingLotsInBox(InBoxParkingLotRequestDto.from(inBoxParkingLotRequestVo)).toVo()
        );
    }

    @Operation(
            summary = "주차장과의 거리 조회",
            description = "사용자의 위치와 주차장 UUID를 통해 주차장과의 거리를 조회하는 API입니다.",
            tags = {"PARKING-LOT-READ-SERVICE"}
    )
    @GetMapping("/{parkingLotUuid}/distance")
    public ApiResponse<UserParkingLotDistanceResponseVo> getDistance(
            @PathVariable String parkingLotUuid,
            @RequestParam double latitude,
            @RequestParam double longitude
    ) {
        return ApiResponse.ok(
                "주차장과의 거리 조회에 성공했습니다.",
                parkingLotFacade.getDistance(
                        UserParkingLotDistanceRequestDto.of(parkingLotUuid, latitude, longitude))
                        .toVo()
        );
    }

    @Operation(
            summary = "주차장 검색",
            description = "키워드를 통해 주차장명으로 검색하는 API입니다.",
            tags = {"PARKING-LOT-READ-SERVICE"}
    )
    @GetMapping("/search")
    public ApiResponse<List<ParkingLotSearchResponseVo>> search(@RequestParam String keyword) {

        return ApiResponse.ok(
                "주차장 검색에 성공했습니다.",
                parkingLotReadService.search(keyword).stream()
                        .map(ParkingLotSearchResponseDto::toVo)
                        .toList()
        );
    }
}
