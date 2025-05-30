package com.parkmate.reservationservice.reservation.presentation;

import com.parkmate.reservationservice.common.response.ApiResponse;
import com.parkmate.reservationservice.reservation.application.ReservationService;
import com.parkmate.reservationservice.reservation.dto.request.ReservationCancelRequestDto;
import com.parkmate.reservationservice.reservation.dto.request.ReservationRequestDto;
import com.parkmate.reservationservice.reservation.vo.request.ReservationCancelRequestVo;
import com.parkmate.reservationservice.reservation.vo.request.ReservationRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{reservationCode}/cancel")
    public ApiResponse<String> cancel(@PathVariable String reservationCode,
                                      @RequestBody ReservationCancelRequestVo ReservationCancelRequestVo) {

        reservationService.cancel(ReservationCancelRequestDto.from(reservationCode, ReservationCancelRequestVo));
        return ApiResponse.ok("예약이 취소되었습니다.");
    }
}
