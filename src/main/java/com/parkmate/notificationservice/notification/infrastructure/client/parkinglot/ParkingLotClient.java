package com.parkmate.notificationservice.notification.infrastructure.client.parkinglot;

import com.parkmate.notificationservice.notification.infrastructure.client.parkinglot.response.ParkingLotAndSpotResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ParkingLotClient {

    private final WebClient webClient;

    private static final String BASE_URL = "http://localhost:8100/internal/parkingLots";

    public CompletableFuture<ParkingLotAndSpotResponse> getParkingLotAndParkingSpotDetails(String parkingLotUuid, Long parkingSpotId) {
        return webClient.get()
                .uri(BASE_URL + "/{parkingLotUuid}/parkingSpots/{parkingSpotId}", parkingLotUuid, parkingSpotId)
                .retrieve()
                .bodyToMono(ParkingLotAndSpotResponse.class)
                .toFuture();
    }
}
