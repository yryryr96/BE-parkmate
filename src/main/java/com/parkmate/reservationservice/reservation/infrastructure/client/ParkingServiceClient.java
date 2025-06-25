package com.parkmate.reservationservice.reservation.infrastructure.client;

import com.parkmate.reservationservice.reservation.infrastructure.client.request.ParkingSpotRequest;
import com.parkmate.reservationservice.reservation.infrastructure.client.response.ParkingLotAndSpotResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "parking-service")
public interface ParkingServiceClient {

    @PostMapping("/internal/parkingSpots/search")
    Optional<ParkingLotAndSpotResponse> getParkingSpots(@RequestBody ParkingSpotRequest parkingSpotRequest);
}
