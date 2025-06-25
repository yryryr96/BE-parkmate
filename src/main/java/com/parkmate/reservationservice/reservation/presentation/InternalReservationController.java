package com.parkmate.reservationservice.reservation.presentation;

import com.parkmate.reservationservice.reservation.application.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/internal/reservations")
@RequiredArgsConstructor
public class InternalReservationController {

    private final ReservationService reservationService;

    @GetMapping("/reservedSpotIds")
    public Set<Long> getReservedParkingSpotIds(@RequestParam String parkingLotUuid,
                                               @RequestParam LocalDateTime entryTime,
                                               @RequestParam LocalDateTime exitTime) {

        return reservationService.getReservedParkingSpotIds(parkingLotUuid, entryTime, exitTime);
    }
}
