package com.parkmate.reservationservice.reservation.presentation;

import com.parkmate.reservationservice.common.response.ApiResponse;
import com.parkmate.reservationservice.common.response.CursorPage;
import com.parkmate.reservationservice.reservation.application.ReservationService;
import com.parkmate.reservationservice.reservation.dto.request.*;
import com.parkmate.reservationservice.reservation.dto.response.ReservationResponseDto;
import com.parkmate.reservationservice.reservation.vo.request.ReservationCancelRequestVo;
import com.parkmate.reservationservice.reservation.vo.request.ReservationCreateRequestVo;
import com.parkmate.reservationservice.reservation.vo.request.ReservationCursorGetRequestVo;
import com.parkmate.reservationservice.reservation.vo.request.ReservationModifyRequestVo;
import com.parkmate.reservationservice.reservation.vo.response.ReservationResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private static final String USER_UUID_HEADER = "X-User-UUID";
    private final ReservationService reservationService;

    @Operation(
            summary = "예약 생성",
            description = "사용자가 주차 공간을 예약합니다. 예약 시 주차 공간의 가용성을 확인하고, 예약이 성공하면 예약 정보를 반환합니다." +
                    "Header에 X-User-UUID를 포함해야 합니다.",
            tags = {"RESERVATION-SERVICE"}
    )
    @PostMapping
    public ApiResponse<String> reserve(@RequestHeader(USER_UUID_HEADER) String userUuid,
                                       @RequestBody ReservationCreateRequestVo reservationCreateRequestVo) {

        reservationService.reserve(ReservationCreateRequestDto.of(userUuid, reservationCreateRequestVo));
        return ApiResponse.created("예약이 완료되었습니다.");
    }

    @Operation(
            summary = "예약 수정",
            description = "사용자가 예약을 수정합니다. 수정 시 예약 코드와 함께 변경할 정보를 포함해야 합니다." +
                    "Header에 X-User-UUID를 포함해야 합니다.",
            tags = {"RESERVATION-SERVICE"}
    )
    @PutMapping("/{reservationCode}")
    public ApiResponse<String> modify(@RequestHeader(USER_UUID_HEADER) String userUuid,
                                      @PathVariable String reservationCode,
                                      @RequestBody ReservationModifyRequestVo reservationModifyRequestVo) {

        reservationService.modify(
                ReservationModifyRequestDto.of(userUuid, reservationCode, reservationModifyRequestVo));
        return ApiResponse.ok("예약이 수정되었습니다.");
    }

    @Operation(
            summary = "예약 취소",
            description = "사용자가 예약을 취소합니다. 취소 시 예약 코드와 함께 취소 사유를 포함해야 합니다." +
                    "Header에 X-User-UUID를 포함해야 합니다.",
            tags = {"RESERVATION-SERVICE"}
    )
    @PutMapping("/{reservationCode}/cancel")
    public ApiResponse<String> cancel(@RequestHeader(USER_UUID_HEADER) String userUuid,
                                      @PathVariable String reservationCode,
                                      @RequestBody ReservationCancelRequestVo ReservationCancelRequestVo) {

        reservationService.cancel(
                ReservationCancelRequestDto.of(userUuid, reservationCode, ReservationCancelRequestVo));
        return ApiResponse.ok("예약이 취소되었습니다.");
    }

    @Operation(
            summary = "예약 조회",
            description = "사용자가 자신의 예약 목록을 조회합니다. Header에 X-User-UUID를 포함해야 합니다.",
            tags = {"RESERVATION-SERVICE"}
    )
    @GetMapping
    public ApiResponse<CursorPage<ReservationResponseVo>> getReservations(
            @RequestHeader(USER_UUID_HEADER) String userUuid,
            @ModelAttribute ReservationCursorGetRequestVo reservationCursorGetRequestVo) {

        return ApiResponse.ok(
                reservationService.getReservations(ReservationCursorGetRequestDto.of(userUuid, reservationCursorGetRequestVo))
                        .map(ReservationResponseDto::toVo)
        );
    }

    @Operation(
            summary = "예약 상세 조회",
            description = "사용자가 특정 예약의 상세 정보를 조회합니다. Header에 X-User-UUID를 포함해야 합니다.",
            tags = {"RESERVATION-SERVICE"}
    )
    @GetMapping("/{reservationCode}")
    public ApiResponse<ReservationResponseVo> getReservation(@RequestHeader(USER_UUID_HEADER) String userUuid,
                                                             @PathVariable String reservationCode) {
        ReservationResponseVo reservation = reservationService.getReservation(
                ReservationGetRequestDto.of(userUuid, reservationCode)).toVo();
        return ApiResponse.ok(reservation);
    }
}
