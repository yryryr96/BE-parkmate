package com.parkmate.parkingservice.parkinglot.presentation;

import com.parkmate.parkingservice.facade.internal.InternalFacade;
import com.parkmate.parkingservice.facade.internal.request.ReservedParkingLotRequest;
import com.parkmate.parkingservice.facade.internal.response.ReservedParkingLotResponse;
import com.parkmate.parkingservice.facade.internal.response.ReservedParkingLotsResponse;
import com.parkmate.parkingservice.facade.parkinglot.ParkingLotFacade;
import com.parkmate.parkingservice.parkinglot.application.ParkingLotService;
import com.parkmate.parkingservice.parkinglot.vo.response.ParkingLotHostUuidResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/parkingLots")
@RequiredArgsConstructor
public class InternalParkingLotController {

    private final ParkingLotService parkingLotService;
    private final InternalFacade internalFacade;

    @Operation(
            summary = "주차장 UUID로 호스트 UUID 조회",
            description = "주차장 UUID를 PathVariable로 받아 해당 주차장의 호스트 UUID를 조회하는 API입니다. " +
                          "이 API는 내부 서비스에서 사용됩니다.",
            tags = {"PARKING-LOT-SERVICE"}
    )
    @GetMapping("/{parkingLotUuid}/host")
    public ParkingLotHostUuidResponseVo getHostUuidByParkingLotUuid(@PathVariable String parkingLotUuid) {
        return parkingLotService.getHostUuidByParkingLotUuid(parkingLotUuid).toVo();
    }

    @Operation(
            summary = "예약된 주차 공간 조회",
            description = "주차장 UUID와 주차 공간 ID를 PathVariable로 받아 해당 예약된 주차 공간 정보를 조회하는 API입니다. " +
                          "이 API는 내부 서비스에서 사용됩니다.",
            tags = {"PARKING-LOT-SERVICE"}
    )
    @GetMapping("/{parkingLotUuid}/parkingSpots/{parkingSpotId}")
    public ReservedParkingLotResponse getReservedParkingLotByParkingLotUuid(@PathVariable String parkingLotUuid,
                                                                            @PathVariable Long parkingSpotId) {

        return internalFacade.getReservedParkingLot(ReservedParkingLotRequest.of(parkingLotUuid, parkingSpotId));
    }

    @Operation(
            summary = "예약된 주차 공간 목록 조회",
            description = "주차장 UUID와 주차 공간 ID 목록을 요청 파라미터로 받아 해당 예약된 주차 공간 목록을 조회하는 API입니다. " +
                          "이 API는 내부 서비스에서 사용됩니다.",
            tags = {"PARKING-LOT-SERVICE"}
    )
    @GetMapping("/parkingSpots")
    public ReservedParkingLotsResponse getReservedParkingLotsByParkingLotUuid(@RequestParam List<String> parkingLotUuids,
                                                                              @RequestParam List<Long> parkingSpotIds) {

        return internalFacade.getReservedParkingLots(parkingLotUuids, parkingSpotIds);
    }
}
