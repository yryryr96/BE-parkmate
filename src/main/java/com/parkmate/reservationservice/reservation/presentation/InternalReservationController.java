package com.parkmate.reservationservice.reservation.presentation;

import com.parkmate.reservationservice.common.response.ApiResponse;
import com.parkmate.reservationservice.reservation.application.ReservationService;
import com.parkmate.reservationservice.reservation.dto.request.ReservationGetForUseDto;
import com.parkmate.reservationservice.reservation.dto.response.ReservationResponseDto;
import com.thoughtworks.xstream.converters.extended.ISO8601DateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/internal/reservations")
@RequiredArgsConstructor
public class InternalReservationController {

    private final ReservationService reservationService;

    private static final String USER_UUID_HEADER = "X-User-UUID";

    @GetMapping("/reservedSpotIds")
    public Set<Long> getReservedParkingSpotIds(@RequestParam String parkingLotUuid,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime entryTime,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime exitTime) {

        return reservationService.getReservedParkingSpotIds(parkingLotUuid, entryTime, exitTime);
    }

    @GetMapping("/{reservationCode}")
    public ApiResponse<ReservationResponseDto> getReservationForUse(
            @RequestHeader(USER_UUID_HEADER) String userUuid,
            @PathVariable String reservationCode,
            @RequestParam String parkingLotUuid
    ) {
        ReservationResponseDto result = reservationService.getReservationForUse(
                ReservationGetForUseDto.of(userUuid, reservationCode, parkingLotUuid)
        );

        return ApiResponse.ok(result);
    }
}
