package com.parkmate.parkingservice.parkinglot.presentation;

import com.parkmate.parkingservice.common.response.ApiResponse;
import com.parkmate.parkingservice.facade.ParkingLotFacade;
import com.parkmate.parkingservice.parkinglot.application.ParkingLotService;
import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotDeleteRequestDto;
import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotRegisterRequestDto;
import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotUpdateRequestDto;
import com.parkmate.parkingservice.parkinglot.dto.response.ParkingLotGeoResponseDto;
import com.parkmate.parkingservice.parkinglot.vo.request.ParkingLotRegisterRequestVo;
import com.parkmate.parkingservice.parkinglot.vo.request.ParkingLotUpdateRequestVo;
import com.parkmate.parkingservice.parkinglot.vo.response.ParkingLotGeoResponseVo;
import com.parkmate.parkingservice.parkinglot.vo.response.ParkingLotHostUuidResponseVo;
import com.parkmate.parkingservice.parkinglot.vo.response.ParkingLotResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parkingLots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotFacade parkingLotFacade;
    private final ParkingLotService parkingLotService;

    @PostMapping
    public ApiResponse<String> registerParkingLot(@RequestBody ParkingLotRegisterRequestVo parkingLotRegisterRequestVo) {
        parkingLotService.register(ParkingLotRegisterRequestDto.from(parkingLotRegisterRequestVo));
        return ApiResponse.of(
                HttpStatus.CREATED,
                "주차장 등록이 완료되었습니다."
        );
    }

    @PutMapping("/{parkingLotUuid}")
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

    @GetMapping("/nearby")
    public ApiResponse<List<ParkingLotGeoResponseVo>> getNearbyParkingLots(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double radius) {

        return ApiResponse.ok(
                parkingLotFacade.getNearbyParkingLotsMysql(latitude, longitude, radius)
                        .stream()
                        .map(ParkingLotGeoResponseDto::toVo)
                        .toList()
        );
    }

    @GetMapping("/{parkingLotUuid}/host")
    public ParkingLotHostUuidResponseVo getHostUuidByParkingLotUuid(@PathVariable String parkingLotUuid) {
        return parkingLotService.getHostUuidByParkingLotUuid(parkingLotUuid).toVo();
    }
}
