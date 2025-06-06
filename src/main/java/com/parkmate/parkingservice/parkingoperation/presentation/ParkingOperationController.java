package com.parkmate.parkingservice.parkingoperation.presentation;

import com.parkmate.parkingservice.common.response.ApiResponse;
import com.parkmate.parkingservice.parkingoperation.application.ParkingOperationService;
import com.parkmate.parkingservice.parkingoperation.dto.request.*;
import com.parkmate.parkingservice.parkingoperation.dto.response.ParkingOperationResponseDto;
import com.parkmate.parkingservice.parkingoperation.vo.ParkingOperationRegisterRequestVo;
import com.parkmate.parkingservice.parkingoperation.vo.ParkingOperationResponseVo;
import com.parkmate.parkingservice.parkingoperation.vo.request.ParkingOperationUpdateRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parkingLots/")
@RequiredArgsConstructor
public class ParkingOperationController {

    private final ParkingOperationService parkingOperationService;

    @Operation(
            summary = "운영시간 등록",
            description = "주차장의 운영시간 정보를 등록하는 API입니다. " +
                    "운영시간 정보는 주차장 UUID와 운영시간 정보를 포함하는 요청 본문을 통해 전달됩니다.",
            tags = {"PARKING-OPERATION-SERVICE"}
    )
    @PostMapping("/{parkingLotUuid}/operations")
    public ApiResponse<String> registerParkingOperation(@PathVariable String parkingLotUuid,
                                                        @RequestBody ParkingOperationRegisterRequestVo parkingOperationCreateRequestVo) {

        parkingOperationService.register(
                ParkingOperationRegisterRequestDto.of(
                        parkingLotUuid,
                        parkingOperationCreateRequestVo)
        );

        return ApiResponse.created("운영시간 정보가 등록되었습니다.");
    }

    @Operation(
            summary = "운영시간 업데이트",
            description = "주차장의 운영시간 정보를 업데이트하는 API입니다. " +
                    "운영시간 정보는 주차장 UUID와 운영시간 UUID, 그리고 업데이트할 정보를 포함하는 요청 본문을 통해 전달됩니다.",
            tags = {"PARKING-OPERATION-SERVICE"}
    )
    @PutMapping("/{parkingLotUuid}/operations/{operationUuid}")
    public ApiResponse<String> updateParkingOperation(@PathVariable String parkingLotUuid,
                                                      @PathVariable String operationUuid,
                                                      @RequestBody ParkingOperationUpdateRequestVo parkingOperationCreateRequestVo) {

        parkingOperationService.update(
                ParkingOperationUpdateRequestDto.of(
                        parkingLotUuid,
                        operationUuid,
                        parkingOperationCreateRequestVo
                )
        );

        return ApiResponse.ok("운영시간 정보가 업데이트되었습니다.");
    }

    @Operation(
            summary = "운영시간 삭제",
            description = "주차장의 운영시간 정보를 삭제하는 API입니다. " +
                    "운영시간 정보는 주차장 UUID와 운영시간 UUID를 PathVariable로 전달합니다.",
            tags = {"PARKING-OPERATION-SERVICE"}
    )
    @DeleteMapping("/{parkingLotUuid}/operations/{operationUuid}")
    public ApiResponse<String> deleteParkingOperation(@PathVariable String parkingLotUuid,
                                                      @PathVariable String operationUuid) {

        parkingOperationService.delete(ParkingOperationDeleteRequestDto.of(parkingLotUuid, operationUuid));

        return ApiResponse.of(
                HttpStatus.NO_CONTENT,
                "운영시간 정보가 삭제되었습니다."
        );
    }

    @Operation(
            summary = "운영시간 목록 조회",
            description = "주차장의 운영시간 목록을 조회하는 API입니다. " +
                    "주차장 UUID는 PathVariable로 연도, 월은 요청 파라미터로 전달합니다.",
            tags = {"PARKING-OPERATION-SERVICE"}
    )
    @GetMapping("/{parkingLotUuid}/operations")
    public ApiResponse<List<ParkingOperationResponseVo>> getParkingOperations(@PathVariable String parkingLotUuid,
                                                                              @RequestParam Integer year,
                                                                              @RequestParam Integer month) {

        return ApiResponse.ok(
                parkingOperationService.getParkingOperationList(
                                ParkingOperationListGetRequestDto.of(parkingLotUuid,
                                        year,
                                        month))
                        .stream()
                        .map(ParkingOperationResponseDto::toVo)
                        .toList()
        );
    }

    @Operation(
            summary = "운영시간 상세 조회",
            description = "주차장의 특정 운영시간 정보를 조회하는 API입니다. " +
                    "주차장 UUID와 운영시간 UUID를 PathVariable로 전달합니다.",
            tags = {"PARKING-OPERATION-SERVICE"}
    )
    @GetMapping("/{parkingLotUuid}/operations/{operationUuid}")
    public ApiResponse<ParkingOperationResponseVo> getParkingOperation(@PathVariable String parkingLotUuid,
                                                                       @PathVariable String operationUuid) {

        return ApiResponse.ok(
                parkingOperationService.getParkingOperation(
                                ParkingOperationGetRequestDto.of(parkingLotUuid,
                                        operationUuid))
                        .toVo()
        );
    }
}