package com.parkmate.userparkinghistoryservice.feign;

import com.parkmate.userparkinghistoryservice.common.response.ApiResponse;
import com.parkmate.userparkinghistoryservice.feign.response.ReservationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "reservation-service")
public interface ReservationClient {

    String USER_UUID_HEADER = "X-User-UUID";

    @GetMapping("/internal/reservations/{reservationCode}")
    ApiResponse<ReservationResponse> getReservation(@RequestHeader(USER_UUID_HEADER) String userUuid,
                                                    @PathVariable String reservationCode,
                                                    @RequestParam String parkingLotUuid);
}
