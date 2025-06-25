package com.parkmate.reservationservice.reservation.infrastructure.client;

import com.parkmate.reservationservice.reservation.infrastructure.client.request.ParkingSpotRequest;
import com.parkmate.reservationservice.reservation.infrastructure.client.response.ParkingLotAndSpotResponse;
import com.parkmate.reservationservice.reservation.infrastructure.client.response.ReservedParkingLotsResponse;
import com.parkmate.reservationservice.reservation.infrastructure.client.response.ReservedParkingSpotResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "parking-service", url = "${parking.service.url}")
//@FeignClient(name = "parking-service")
public interface ParkingServiceClient {

    @PostMapping("/internal/parkingSpots/search")
    Optional<ParkingLotAndSpotResponse> getParkingSpots(@RequestBody ParkingSpotRequest parkingSpotRequest);

    @GetMapping("/internal/parkingLots/{parkingLotUuid}/parkingSpots/{parkingSpotId}")
    ReservedParkingSpotResponse getReservedParkingSpot(@PathVariable String parkingLotUuid,
                                                       @PathVariable Long parkingSpotId);

    @GetMapping("/internal/parkingLots/parkingSpots")
    ReservedParkingLotsResponse getReservedParkingSpots(@RequestParam List<String> parkingLotUuids,
                                                        @RequestParam List<Long> parkingSpotIds);
}
