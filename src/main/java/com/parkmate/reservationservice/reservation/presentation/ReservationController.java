package com.parkmate.reservationservice.reservation.presentation;

import com.parkmate.reservationservice.common.response.ApiResponse;
import com.parkmate.reservationservice.reservation.application.ReservationService;
import com.parkmate.reservationservice.reservation.dto.request.ReservationRequestDto;
import com.parkmate.reservationservice.reservation.vo.request.ReservationRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
