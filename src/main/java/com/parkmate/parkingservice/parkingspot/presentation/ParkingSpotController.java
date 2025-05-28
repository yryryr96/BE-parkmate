package com.parkmate.parkingservice.parkingspot.presentation;

import com.parkmate.parkingservice.common.response.ApiResponse;
import com.parkmate.parkingservice.parkingspot.application.ParkingSpotService;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotDeleteRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotGetRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotUpdateRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotResponseDto;
import com.parkmate.parkingservice.parkingspot.vo.request.ParkingSpotUpdateRequestVo;
import com.parkmate.parkingservice.parkingspot.vo.response.ParkingSpotResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parkingLots/")
@RequiredArgsConstructor
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @PutMapping("/{parkingLotUuid}/spots/{parkingSpotId}")
    public ApiResponse<String> updateParkingSpot(@PathVariable String parkingLotUuid,
                                                 @PathVariable Long parkingSpotId,
                                                 @RequestBody ParkingSpotUpdateRequestVo parkingSpotUpdateRequestVo) {
        parkingSpotService.update(ParkingSpotUpdateRequestDto.of(parkingLotUuid, parkingSpotId, parkingSpotUpdateRequestVo));
        return ApiResponse.of(HttpStatus.OK, "주차 공간 정보가 업데이트되었습니다.");
    }

    @DeleteMapping("/{parkingLotUuid}/spots/{parkingSpotId}")
    public ApiResponse<String> deleteParkingSpot(@PathVariable String parkingLotUuid,
                                                 @PathVariable Long parkingSpotId) {
        parkingSpotService.delete(ParkingSpotDeleteRequestDto.of(parkingLotUuid, parkingSpotId));
        return ApiResponse.of(HttpStatus.NO_CONTENT, "주차 공간 정보가 삭제되었습니다.");
    }

    @GetMapping("/{parkingLotUuid}/spots")
    public ApiResponse<List<ParkingSpotResponseVo>> getParkingSpots(@PathVariable String parkingLotUuid) {

        return ApiResponse.ok(parkingSpotService.getParkingSpots(parkingLotUuid)
                .stream()
                .map(ParkingSpotResponseDto::toVo)
                .toList());
    }

    @GetMapping("/{parkingLotUuid}/spots/{parkingSpotId}")
    public ApiResponse<ParkingSpotResponseVo> getParkingSpot(@PathVariable String parkingLotUuid,
                                                             @PathVariable Long parkingSpotId) {
        return ApiResponse.ok(
                parkingSpotService.getParkingSpot(ParkingSpotGetRequestDto.of(parkingLotUuid, parkingSpotId))
                        .toVo()
        );
    }
}
