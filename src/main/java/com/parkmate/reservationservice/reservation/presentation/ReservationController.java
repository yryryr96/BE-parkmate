package com.parkmate.reservationservice.reservation.presentation;

import com.parkmate.reservationservice.common.response.ApiResponse;
import com.parkmate.reservationservice.reservation.application.ReservationService;
import com.parkmate.reservationservice.reservation.dto.request.ReservationCancelRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationCreateRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationGetRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationModifyRequestDto;
import com.parkmate.reservationservice.reservation.vo.request.ReservationCancelRequestVo;
import com.parkmate.reservationservice.reservation.vo.request.ReservationCreateRequestVo;
import com.parkmate.reservationservice.reservation.vo.request.ReservationModifyRequestVo;
import com.parkmate.reservationservice.reservation.vo.response.ReservationResponseVo;
import com.parkmate.reservationservice.reservation.vo.response.ReservationsResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ApiResponse<String> reserve(@RequestHeader("X-User-UUID") String userUuid,
                                       @RequestBody ReservationCreateRequestVo reservationCreateRequestVo) {

        reservationService.reserve(ReservationCreateRequestDto.of(userUuid, reservationCreateRequestVo));
        return ApiResponse.created("예약이 완료되었습니다.");
    }

    @PutMapping("/{reservationCode}")
    public ApiResponse<String> modify(@RequestHeader("X-User-UUID") String userUuid,
                                      @PathVariable String reservationCode,
                                      @RequestBody ReservationModifyRequestVo reservationModifyRequestVo) {

        reservationService.modify(
                ReservationModifyRequestDto.of(userUuid, reservationCode, reservationModifyRequestVo));
        return ApiResponse.ok("예약이 수정되었습니다.");
    }

    @PutMapping("/{reservationCode}/cancel")
    public ApiResponse<String> cancel(@RequestHeader("X-User-UUID") String userUuid,
                                      @PathVariable String reservationCode,
                                      @RequestBody ReservationCancelRequestVo ReservationCancelRequestVo) {

        reservationService.cancel(
                ReservationCancelRequestDto.of(userUuid, reservationCode, ReservationCancelRequestVo));
        return ApiResponse.ok("예약이 취소되었습니다.");
    }

    @GetMapping
    public ApiResponse<ReservationsResponseVo> getReservations(@RequestHeader("X-User-UUID") String userUuid) {
        return ApiResponse.ok(reservationService.getReservations(userUuid).toVo());
    }

    @GetMapping("/{reservationCode}")
    public ApiResponse<ReservationResponseVo> getReservation(@RequestHeader("X-User-UUID") String userUuid,
                                                             @PathVariable String reservationCode) {
        ReservationResponseVo reservation = reservationService.getReservation(
                ReservationGetRequestDto.of(userUuid, reservationCode)).toVo();
        return ApiResponse.ok(reservation);
    }
}
