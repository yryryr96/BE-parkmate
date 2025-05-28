package com.parkmate.parkingservice.parkingspot.presentation;

import com.parkmate.parkingservice.common.response.ApiResponse;
import com.parkmate.parkingservice.parkingspot.application.ParkingSpotService;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotDeleteRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotGetRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotUpdateRequestDto;
import com.parkmate.parkingservice.parkingspot.vo.request.ParkingSpotUpdateRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parkingLots/")
@RequiredArgsConstructor
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @PutMapping("/{parkingLotUuid}/spots/{parkingSpotId}")
    public ApiResponse<String> updateParkingSpot(@PathVariable String parkingLotUuid,
                                                 @PathVariable Long parkingSpotId,
                                                 @RequestBody ParkingSpotUpdateRequestVo parkingSpotUpdateRequestVo) {
        parkingSpotService.update(ParkingSpotUpdateRequestDto.from(parkingLotUuid, parkingSpotId, parkingSpotUpdateRequestVo));
        return ApiResponse.of(HttpStatus.OK, "주차 공간 정보가 업데이트되었습니다.");
    }

}
