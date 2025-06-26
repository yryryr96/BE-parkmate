package com.parkmate.parkingservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "reservation-service")
public interface ReservationClient {

    @GetMapping("/internal/reservations/reservedSpotIds")
    List<Long> getReservations(@RequestParam String parkingLotUuid,
                               @RequestParam LocalDateTime entryTime,
                               @RequestParam LocalDateTime exitTime);

}
