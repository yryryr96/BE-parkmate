package com.parkmate.parkingservice.parkingspot.presentation;

import com.parkmate.parkingservice.common.response.ApiResponse;
import com.parkmate.parkingservice.parkingspot.application.ParkingSpotService;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotDeleteRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotGetRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotRegisterRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotUpdateRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotResponseDto;
import com.parkmate.parkingservice.parkingspot.vo.request.ParkingSpotRegisterRequestVo;
import com.parkmate.parkingservice.parkingspot.vo.request.ParkingSpotUpdateRequestVo;
import com.parkmate.parkingservice.parkingspot.vo.response.ParkingSpotResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parkingLots/")
@RequiredArgsConstructor
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @Operation(
            summary = "주차 공간 등록",
            description = "주차 공간 정보를 등록하는 API입니다. " +
                    "주차 공간 UUID를 PathVariable로 받고, 등록할 정보를 RequestBody로 받습니다.",
            tags = {"PARKING-SPOT-SERVICE"}
    )
    @PostMapping("/{parkingLotUuid}/spots")
    public ApiResponse<String> registerParkingSpot(@PathVariable String parkingLotUuid,
                                                   @RequestBody ParkingSpotRegisterRequestVo parkingSpotRegisterRequestVo) {

        parkingSpotService.register(ParkingSpotRegisterRequestDto.of(parkingLotUuid, parkingSpotRegisterRequestVo));
        return ApiResponse.of(HttpStatus.CREATED, "주차 공간 정보가 등록되었습니다.");
    }

    @Operation(
            summary = "주차 공간 정보 업데이트",
            description = "주차 공간 정보를 업데이트하는 API입니다. " +
                    "주차 공간 UUID를 PathVariable로 받고, 업데이트할 정보를 RequestBody로 받습니다. " +
                          "ParkingSpotType은 enum 타입으로 COMPACT, SMALL, MEDIUM, LARGE, EV 중 하나입니다. " +
                          "evChargeType은 enum 타입으로 AC_SINGLE, DC_COMBO, DC_CHADEMO, AC_THREE_PHASE 중 하나입니다.",
            tags = {"PARKING-SPOT-SERVICE"}
    )
    @PutMapping("/{parkingLotUuid}/spots/{parkingSpotId}")
    public ApiResponse<String> updateParkingSpot(@PathVariable String parkingLotUuid,
                                                 @PathVariable Long parkingSpotId,
                                                 @RequestBody ParkingSpotUpdateRequestVo parkingSpotUpdateRequestVo) {

        parkingSpotService.update(ParkingSpotUpdateRequestDto.of(parkingLotUuid, parkingSpotId, parkingSpotUpdateRequestVo));
        return ApiResponse.of(HttpStatus.OK, "주차 공간 정보가 업데이트되었습니다.");
    }

    @Operation(
            summary = "주차 공간 삭제",
            description = "주차 공간 정보를 삭제하는 API입니다. " +
                    "주차 공간 UUID를 PathVariable로 받습니다.",
            tags = {"PARKING-SPOT-SERVICE"}
    )
    @DeleteMapping("/{parkingLotUuid}/spots/{parkingSpotId}")
    public ApiResponse<String> deleteParkingSpot(@PathVariable String parkingLotUuid,
                                                 @PathVariable Long parkingSpotId) {

        parkingSpotService.delete(ParkingSpotDeleteRequestDto.of(parkingLotUuid, parkingSpotId));
        return ApiResponse.of(HttpStatus.NO_CONTENT, "주차 공간 정보가 삭제되었습니다.");
    }

    @Operation(
            summary = "주차 공간 목록 조회",
            description = "주차 공간 목록을 조회하는 API입니다. " +
                    "주차장 UUID를 PathVariable로 받습니다.",
            tags = {"PARKING-SPOT-SERVICE"}
    )
    @GetMapping("/{parkingLotUuid}/spots")
    public ApiResponse<List<ParkingSpotResponseVo>> getParkingSpots(@PathVariable String parkingLotUuid) {

        return ApiResponse.ok(parkingSpotService.getParkingSpots(parkingLotUuid)
                .stream()
                .map(ParkingSpotResponseDto::toVo)
                .toList());
    }

    @Operation(
            summary = "주차 공간 상세 조회",
            description = "주차 공간의 상세 정보를 조회하는 API입니다. " +
                    "주차장 UUID와 주차 공간 ID를 PathVariable로 받습니다.",
            tags = {"PARKING-SPOT-SERVICE"}
    )
    @GetMapping("/{parkingLotUuid}/spots/{parkingSpotId}")
    public ApiResponse<ParkingSpotResponseVo> getParkingSpot(@PathVariable String parkingLotUuid,
                                                             @PathVariable Long parkingSpotId) {

        return ApiResponse.ok(
                parkingSpotService.getParkingSpot(ParkingSpotGetRequestDto.of(parkingLotUuid, parkingSpotId))
                        .toVo()
        );
    }
}
