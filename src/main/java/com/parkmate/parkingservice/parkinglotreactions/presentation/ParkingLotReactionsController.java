package com.parkmate.parkingservice.parkinglotreactions.presentation;

import com.parkmate.parkingservice.common.response.ApiResponse;
import com.parkmate.parkingservice.parkinglotreactions.application.ParkingLotReactionsService;
import com.parkmate.parkingservice.parkinglotreactions.dto.request.ParkingLotReactionRequestDto;
import com.parkmate.parkingservice.parkinglotreactions.vo.request.ParkingLotReactionRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parkingLots/reactions")
@RequiredArgsConstructor
public class ParkingLotReactionsController {

    private final ParkingLotReactionsService parkingLotReactionsService;

    @Operation(
            summary = "주차장 반응 추가",
            description = "주차장에 대한 반응을 추가하는 API입니다. " +
                          "ReactionType은 LIKE 또는 DISLIKE 중 하나입니다.",
            tags = {"PARKING-LOT-REACTIONS"}
    )
    @PostMapping
    public ApiResponse<String> addReaction(@RequestBody ParkingLotReactionRequestVo parkingLotReactionRequestVo) {
        parkingLotReactionsService.addReaction(ParkingLotReactionRequestDto.from(parkingLotReactionRequestVo));
        return ApiResponse.created(
                "주차장 반응이 추가되었습니다."
        );
    }
}
