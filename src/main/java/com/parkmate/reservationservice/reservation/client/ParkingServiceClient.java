package com.parkmate.reservationservice.reservation.client;

import com.parkmate.reservationservice.reservation.client.response.ParkingLotClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "parking-service", url = "${parking.service.url}")
public interface ParkingServiceClient {

    @GetMapping("/api/v1/parkingLots/{parkingLotUuid}/host")
    ParkingLotClientResponse getHostUuidByParkingLotUuid(@PathVariable String parkingLotUuid);
}
