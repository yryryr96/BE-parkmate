package com.parkmate.parking_service.parking_operation.presentation;

import com.parkmate.parking_service.common.response.ApiResponse;
import com.parkmate.parking_service.parking_operation.application.ParkingOperationService;
import com.parkmate.parking_service.parking_operation.dto.request.ParkingOperationCreateRequestDto;
import com.parkmate.parking_service.parking_operation.dto.request.ParkingOperationGetRequestDto;
import com.parkmate.parking_service.parking_operation.dto.request.ParkingOperationListGetRequestDto;
import com.parkmate.parking_service.parking_operation.dto.request.ParkingOperationUpdateRequestDto;
import com.parkmate.parking_service.parking_operation.dto.response.ParkingOperationResponseDto;
import com.parkmate.parking_service.parking_operation.vo.ParkingOperationCreateRequestVo;
import com.parkmate.parking_service.parking_operation.vo.ParkingOperationResponseVo;
import com.parkmate.parking_service.parking_operation.vo.request.ParkingOperationUpdateRequestVo;
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
    public ApiResponse<String> registerParkingOperation(
            @PathVariable String parkingLotUuid,
            @RequestBody ParkingOperationCreateRequestVo parkingOperationCreateRequestVo) {

        parkingOperationService.register(
                ParkingOperationCreateRequestDto.of(
                        parkingLotUuid, parkingOperationCreateRequestVo)
        );

        return ApiResponse.of(
                HttpStatus.CREATED,
                "운영시간 정보가 등록되었습니다."
        );
    }

    @PutMapping("/{parkingLotUuid}/operations/{operationUuid}")
    public ApiResponse<String> updateParkingOperation(@PathVariable String parkingLotUuid,
                                                      @PathVariable String operationUuid,
                                                      @RequestBody ParkingOperationUpdateRequestVo parkingOperationCreateRequestVo) {

        parkingOperationService.update(
                ParkingOperationUpdateRequestDto.of(
                        parkingLotUuid, operationUuid, parkingOperationCreateRequestVo
                )
        );

        return ApiResponse.of(
                HttpStatus.OK,
                "운영시간 정보가 업데이트되었습니다."
        );
    }

    @GetMapping("/{parkingLotUuid}/operations")
    public ApiResponse<List<ParkingOperationResponseVo>> getParkingOperations(@PathVariable String parkingLotUuid,
                                                                              @RequestParam Integer year,
                                                                              @RequestParam Integer month) {

        return ApiResponse.ok(
                parkingOperationService.getParkingOperationList(
                                ParkingOperationListGetRequestDto.of(parkingLotUuid, year, month))
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
                                ParkingOperationGetRequestDto.of(parkingLotUuid, operationUuid))
                        .toVo()
        );
    }
}