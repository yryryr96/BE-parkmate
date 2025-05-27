package com.parkmate.parking_service.parkinglot.presentation;

import com.parkmate.parking_service.common.response.ApiResponse;
import com.parkmate.parking_service.parkinglot.application.ParkingLotService;
import com.parkmate.parking_service.parkinglot.dto.request.ParkingLotCreateRequestDto;
import com.parkmate.parking_service.parkinglot.dto.request.ParkingLotDeleteRequestDto;
import com.parkmate.parking_service.parkinglot.dto.request.ParkingLotUpdateRequestDto;
import com.parkmate.parking_service.parkinglot.vo.request.ParkingLotCreateRequestVo;
import com.parkmate.parking_service.parkinglot.vo.request.ParkingLotUpdateRequestVo;
import com.parkmate.parking_service.parkinglot.vo.response.ParkingLotResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parkingLots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @PostMapping
    public ApiResponse<String> registerParkingLot(@RequestBody ParkingLotCreateRequestVo parkingLotCreateRequestVo) {
        parkingLotService.register(ParkingLotCreateRequestDto.from(parkingLotCreateRequestVo));
        return ApiResponse.of(
                HttpStatus.CREATED,
                "주차장 등록이 완료되었습니다."
        );
    }

    @PostMapping("/{parkingLotUuid}")
    public ApiResponse<String> updateParkingLot(@PathVariable String parkingLotUuid,
                                                @RequestBody ParkingLotUpdateRequestVo parkingLotUpdateRequestVo) {

        parkingLotService.update(ParkingLotUpdateRequestDto.from(parkingLotUuid, parkingLotUpdateRequestVo));
        return ApiResponse.of(
                HttpStatus.OK,
                "주차장 정보가 업데이트되었습니다."
        );
    }

    @DeleteMapping("/{parkingLotUuid}")
    public ApiResponse<String> deleteParkingLot(@PathVariable String parkingLotUuid,
                                                @RequestParam("hostUuid") String hostUuid) {
        parkingLotService.delete(ParkingLotDeleteRequestDto.of(parkingLotUuid, hostUuid));
        return ApiResponse.of(
                HttpStatus.NO_CONTENT,
                "주차장 정보가 삭제되었습니다."
        );
    }

    @GetMapping("/{parkingLotUuid}")
    public ApiResponse<ParkingLotResponseVo> getParkingLotByUuid(@PathVariable String parkingLotUuid) {
        return ApiResponse.ok(
                parkingLotService.findByUuid(parkingLotUuid).toVo()
        );
    }
}
