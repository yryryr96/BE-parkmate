package com.parkmate.reservationservice.reservation.presentation;

import com.parkmate.reservationservice.common.response.ApiResponse;
import com.parkmate.reservationservice.reservation.application.ReservationService;
import com.parkmate.reservationservice.reservation.dto.request.ReservationCancelRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationGetRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationModifyRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationRequestDto;
import com.parkmate.reservationservice.reservation.dto.response.ReservationResponseDto;
import com.parkmate.reservationservice.reservation.vo.request.ReservationCancelRequestVo;
import com.parkmate.reservationservice.reservation.vo.request.ReservationModifyRequestVo;
import com.parkmate.reservationservice.reservation.vo.request.ReservationRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ApiResponse<String> reserve(@RequestBody ReservationRequestVo reservationRequestVo) {

        reservationService.reserve(ReservationRequestDto.from(reservationRequestVo));
        return ApiResponse.ok("예약이 완료되었습니다.");
    }

    @PutMapping("/{reservationCode}")
    public ApiResponse<String> modify(@PathVariable String reservationCode,
                                      @RequestBody ReservationModifyRequestVo reservationModifyRequestVo) {

        reservationService.modify(ReservationModifyRequestDto.of(reservationCode, reservationModifyRequestVo));
        return ApiResponse.ok("예약이 수정되었습니다.");
    }

    @PutMapping("/{reservationCode}/cancel")
    public ApiResponse<String> cancel(@PathVariable String reservationCode,
                                      @RequestBody ReservationCancelRequestVo ReservationCancelRequestVo) {

        reservationService.cancel(ReservationCancelRequestDto.from(reservationCode, ReservationCancelRequestVo));
        return ApiResponse.ok("예약이 취소되었습니다.");
    }

    @GetMapping
    public ApiResponse<List<ReservationResponseDto>> getReservations(@RequestParam String userUuid) {
        List<ReservationResponseDto> reservations = reservationService.getReservations(userUuid);
        return ApiResponse.ok(reservations);
    }

    @GetMapping("/{reservationCode}")
    public ApiResponse<ReservationResponseDto> getReservation(@PathVariable String reservationCode,
                                                              @RequestParam String userUuid) {
        ReservationResponseDto reservation = reservationService.getReservation(
                ReservationGetRequestDto.of(reservationCode, userUuid));
        return ApiResponse.ok(reservation);
    }
}
