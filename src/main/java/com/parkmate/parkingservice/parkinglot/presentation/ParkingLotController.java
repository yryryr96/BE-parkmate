package com.parkmate.parkingservice.parkinglot.presentation;

import com.parkmate.parkingservice.common.response.ApiResponse;
import com.parkmate.parkingservice.facade.ParkingLotFacade;
import com.parkmate.parkingservice.facade.dto.ParkingLotRegisterRequest;
import com.parkmate.parkingservice.parkinglot.application.ParkingLotService;
import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotDeleteRequestDto;
import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotUpdateRequestDto;
import com.parkmate.parkingservice.parkinglot.vo.request.ParkingLotUpdateRequestVo;
import com.parkmate.parkingservice.parkinglot.vo.response.ParkingLotHostUuidResponseVo;
import com.parkmate.parkingservice.parkinglot.vo.response.ParkingLotResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parkingLots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotFacade parkingLotFacade;
    private final ParkingLotService parkingLotService;

    @Operation(
            summary = "주차장 등록",
            description = "parkingLot, parkingSpot, parkingLotImage 요청을 받아 주차장을 등록하는 API입니다. " +
                          "parkingLotType은 enum 타입으로 PRIVATE, PUBLIC, COMMERCIAL 중 하나입니다. " +
                          "parkingSpotType은 enum 타입으로 COMPACT, SMALL, MEDIUM, LARGE, EV 중 하나입니다. " +
                          "chargeable 요청에는 parkingSpot을 EV로 요청해주세요. " +
                          "evChargeType은 enum 타입으로 AC_SINGLE, DC_COMBO, DC_CHADEMO, AC_THREE_PHASE 중 하나입니다.",
            tags = {"PARKING-LOT-SERVICE"}
    )
    @PostMapping
    public ApiResponse<String> registerParkingLot(@RequestBody ParkingLotRegisterRequest parkingLotRegisterRequest) {
        parkingLotFacade.registerParkingLot(parkingLotRegisterRequest);
        return ApiResponse.of(
                HttpStatus.CREATED,
                "주차장 등록이 완료되었습니다."
        );
    }

    @Operation(
            summary = "주차장 정보 업데이트",
            description = "주차장 정보를 업데이트하는 API입니다. " +
                          "주차장 UUID를 PathVariable로 받고, 업데이트할 정보를 RequestBody로 받습니다.",
            tags = {"PARKING-LOT-SERVICE"}
    )
    @PutMapping("/{parkingLotUuid}")
    public ApiResponse<String> updateParkingLot(@PathVariable String parkingLotUuid,
                                                @RequestHeader("X-Host-UUID") String hostUuid,
                                                @RequestBody ParkingLotUpdateRequestVo parkingLotUpdateRequestVo) {

        parkingLotService.update(ParkingLotUpdateRequestDto.from(parkingLotUuid, hostUuid, parkingLotUpdateRequestVo));
        return ApiResponse.of(
                HttpStatus.OK,
                "주차장 정보가 업데이트되었습니다."
        );
    }

    @Operation(
            summary = "주차장 정보 삭제",
            description = "주차장 정보를 삭제하는 API입니다. " +
                          "주차장 UUID를 PathVariable로 받고, 호스트 UUID를 RequestParam으로 받습니다.",
            tags = {"PARKING-LOT-SERVICE"}
    )
    @DeleteMapping("/{parkingLotUuid}")
    public ApiResponse<String> deleteParkingLot(@PathVariable String parkingLotUuid,
                                                @RequestHeader("X-Host-UUID") String hostUuid) {
        parkingLotService.delete(ParkingLotDeleteRequestDto.of(parkingLotUuid, hostUuid));
        return ApiResponse.of(
                HttpStatus.NO_CONTENT,
                "주차장 정보가 삭제되었습니다."
        );
    }

    @Operation(
            summary = "주차장 UUID로 주차장 정보 조회",
            description = "주차장 UUID를 PathVariable로 받아 해당 주차장의 정보를 조회하는 API입니다.",
            tags = {"PARKING-LOT-SERVICE"}
    )
    @GetMapping("/{parkingLotUuid}")
    public ApiResponse<ParkingLotResponseVo> getParkingLotByUuid(@PathVariable String parkingLotUuid) {
        return ApiResponse.ok(
                parkingLotService.findByUuid(parkingLotUuid).toVo()
        );
    }

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
