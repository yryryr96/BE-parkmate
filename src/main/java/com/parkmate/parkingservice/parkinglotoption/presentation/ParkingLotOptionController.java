package com.parkmate.parkingservice.parkinglotoption.presentation;

import com.parkmate.parkingservice.common.response.ApiResponse;
import com.parkmate.parkingservice.parkinglotoption.application.ParkingLotOptionService;
import com.parkmate.parkingservice.parkinglotoption.dto.request.ParkingLotOptionRegisterRequestDto;
import com.parkmate.parkingservice.parkinglotoption.vo.request.ParkingLotOptionRegisterRequestVo;
import com.parkmate.parkingservice.parkinglotoption.vo.response.ParkingLotOptionResponseVo;
import com.parkmate.parkingservice.parkinglotoption.vo.response.ParkingLotOptionResponseVoList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parkingLotOptions")
@RequiredArgsConstructor
public class ParkingLotOptionController {

    private final ParkingLotOptionService parkingLotOptionService;

    @PostMapping
    public ApiResponse<ParkingLotOptionResponseVo> registerOption(@RequestBody ParkingLotOptionRegisterRequestVo parkingLotOptionRegisterRequestVo) {

        return ApiResponse.ok(
                "주차장 옵션 생성 성공",
                parkingLotOptionService.registerOption(ParkingLotOptionRegisterRequestDto.from(parkingLotOptionRegisterRequestVo))
                .toVo()
        );
    }

    @GetMapping
    public ApiResponse<ParkingLotOptionResponseVoList> getParkingLotOptions() {

        return ApiResponse.ok(
                "주차장 옵션 조회 성공",
                parkingLotOptionService.getOptions().toVo()
        );
    }
}
