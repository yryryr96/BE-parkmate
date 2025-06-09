package com.parkmate.parkingservice.parkinglotreactions.presentation;

import com.parkmate.parkingservice.common.response.ApiResponse;
import com.parkmate.parkingservice.parkinglotreactions.application.ParkingLotReactionsService;
import com.parkmate.parkingservice.parkinglotreactions.domain.ReactionType;
import com.parkmate.parkingservice.parkinglotreactions.dto.request.ParkingLotReactionGetRequestDto;
import com.parkmate.parkingservice.parkinglotreactions.dto.request.ParkingLotReactionUpsertRequestDto;
import com.parkmate.parkingservice.parkinglotreactions.vo.request.ParkingLotReactionUpsertRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parkingLots")
@RequiredArgsConstructor
public class ParkingLotReactionsController {

    private final ParkingLotReactionsService parkingLotReactionsService;

    @Operation(
            summary = "주차장 반응 추가",
            description = "주차장에 대한 반응을 추가하는 API입니다. " +
                          "ReactionType은 LIKE 또는 DISLIKE 중 하나입니다.",
            tags = {"PARKING-LOT-REACTIONS"}
    )
    @PostMapping("/{parkingLotUuid}/reactions")
    public ApiResponse<String> addReaction(@PathVariable String parkingLotUuid,
                                           @RequestHeader("X-User-UUID") String userUuid,
                                           @RequestBody ParkingLotReactionUpsertRequestVo parkingLotReactionUpsertRequestVo) {
        parkingLotReactionsService.addReaction(ParkingLotReactionUpsertRequestDto.of(parkingLotUuid, userUuid, parkingLotReactionUpsertRequestVo));
        return ApiResponse.created(
                "주차장 반응이 추가되었습니다."
        );
    }

    @Operation(
            summary = "주차장 반응 조회",
            description = "주차장에 대한 사용자의 반응을 조회하는 API입니다. " +
                          "data 응답은 NONE, LIKE, DISLIKE 중 하나입니다.",
            tags = {"PARKING-LOT-REACTIONS"}
    )
    @GetMapping("/{parkingLotUuid}/reactions")
    public ApiResponse<ReactionType> getReaction(@PathVariable String parkingLotUuid,
                                                 @RequestHeader("X-User-UUID") String userUuid) {

        return ApiResponse.ok(parkingLotReactionsService.getReaction(ParkingLotReactionGetRequestDto.of(parkingLotUuid, userUuid)));
    }
}
