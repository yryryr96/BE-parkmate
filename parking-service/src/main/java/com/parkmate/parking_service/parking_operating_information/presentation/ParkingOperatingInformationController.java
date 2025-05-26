package com.parkmate.parking_service.parking_operating_information.presentation;

import com.parkmate.parking_service.common.response.ApiResponse;
import com.parkmate.parking_service.parking_operating_information.application.ParkingOperatingInformationService;
import com.parkmate.parking_service.parking_operating_information.dto.ParkingOperatingInformationCreateRequestDto;
import com.parkmate.parking_service.parking_operating_information.vo.ParkingOperatingInformationCreateRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parkingLots/")
@RequiredArgsConstructor
public class ParkingOperatingInformationController {

    private final ParkingOperatingInformationService parkingOperatingInformationService;

    @PostMapping("/{parkingLotUuid}/operatingInformation")
    public ApiResponse<String> registerParkingOperatingInformation(
            @PathVariable String parkingLotUuid,
            @RequestBody ParkingOperatingInformationCreateRequestVo parkingOperatingInformationCreateRequestVo) {

        parkingOperatingInformationService.register(
                ParkingOperatingInformationCreateRequestDto.of(
                        parkingLotUuid, parkingOperatingInformationCreateRequestVo)
        );

        return ApiResponse.of(
                HttpStatus.CREATED,
                "운영시간 정보가 등록되었습니다."
        );
    }
}
