package com.parkmate.parking_service.parking_operation.presentation;

//import com.parkmate.parking_service.common.response.ApiResponse;

import com.parkmate.parking_service.parking_operation.application.ParkingOperationService;
import com.parkmate.parking_service.parking_operation.dto.request.ParkingOperationCreateRequestDto;
import com.parkmate.parking_service.parking_operation.dto.request.ParkingOperationGetRequestDto;
import com.parkmate.parking_service.parking_operation.dto.response.ParkingOperationResponseDto;
import com.parkmate.parking_service.parking_operation.vo.ParkingOperationCreateRequestVo;
import com.parkmate.parking_service.parking_operation.vo.ParkingOperationResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parkingLots/")
@RequiredArgsConstructor
public class ParkingOperationController {

    private final ParkingOperationService parkingOperationService;

    @PostMapping("/{parkingLotUuid}/operations")
    public String registerParkingOperation(
            @PathVariable String parkingLotUuid,
            @RequestBody ParkingOperationCreateRequestVo parkingOperationCreateRequestVo) {

        parkingOperationService.register(
                ParkingOperationCreateRequestDto.of(
                        parkingLotUuid, parkingOperationCreateRequestVo)
        );

        return "OK";
//        return ApiResponse.of(
//                HttpStatus.CREATED,
//                "운영시간 정보가 등록되었습니다."
//        );
    }

    @GetMapping("/{parkingLotUuid}/operations")
    public List<ParkingOperationResponseVo> getParkingOperations(@PathVariable String parkingLotUuid,
                                                                 @RequestParam Integer year,
                                                                 @RequestParam Integer month) {

        return parkingOperationService.getParkingOperations(
                        ParkingOperationGetRequestDto.of(parkingLotUuid, year, month))
                .stream()
                .map(ParkingOperationResponseDto::toVo)
                .toList();
    }

//    @GetMapping("/{parkingLotUuid}/operations/{operationDate}")
}