package com.parkmate.parkingreadservice.parkingoperation.presentation;

import com.parkmate.parkingreadservice.common.response.ApiResponse;
import com.parkmate.parkingreadservice.parkingoperation.application.ParkingLotOperationReadService;
import com.parkmate.parkingreadservice.parkingoperation.dto.response.ParkingLotOperationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/v1/operations/test")
@RestController
@RequiredArgsConstructor
public class OperationTestController {

    private final ParkingLotOperationReadService parkingLotOperationReadService;

//    @GetMapping("/{parkingLotUuid}")
//    public ApiResponse<List<ParkingLotOperationResponseDto>> testOperation(@PathVariable String parkingLotUuid,
//                                                                           @RequestParam LocalDateTime requestStart,
//                                                                           @RequestParam LocalDateTime requestEnd) {
//
//        return ApiResponse.ok(
//                parkingLotOperationReadService.getOperationsByUuidAndDateRange(
//                        List.of(parkingLotUuid),
//                        requestStart,
//                        requestEnd
//                )
//        );
//    }
}
