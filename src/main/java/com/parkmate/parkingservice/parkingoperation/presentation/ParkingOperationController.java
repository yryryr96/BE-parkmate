package com.parkmate.parkingservice.parkingoperation.presentation;

import com.parkmate.parkingservice.common.response.ApiResponse;
import com.parkmate.parkingservice.parkingoperation.application.ParkingOperationService;
import com.parkmate.parkingservice.parkingoperation.dto.request.*;
import com.parkmate.parkingservice.parkingoperation.dto.response.ParkingOperationResponseDto;
import com.parkmate.parkingservice.parkingoperation.vo.ParkingOperationRegisterRequestVo;
import com.parkmate.parkingservice.parkingoperation.vo.ParkingOperationResponseVo;
import com.parkmate.parkingservice.parkingoperation.vo.request.ParkingOperationUpdateRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parkingLots/")
@RequiredArgsConstructor
public class ParkingOperationController {

    private final ParkingOperationService parkingOperationService;

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

    @DeleteMapping("/{parkingLotUuid}/operations/{operationUuid}")
    public ApiResponse<String> deleteParkingOperation(@PathVariable String parkingLotUuid,
                                                      @PathVariable String operationUuid) {

        parkingOperationService.delete(ParkingOperationDeleteRequestDto.of(parkingLotUuid, operationUuid));

        return ApiResponse.of(
                HttpStatus.NO_CONTENT,
                "운영시간 정보가 삭제되었습니다."
        );
    }

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