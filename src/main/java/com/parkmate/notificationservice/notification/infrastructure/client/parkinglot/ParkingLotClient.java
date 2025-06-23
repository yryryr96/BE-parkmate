package com.parkmate.notificationservice.notification.infrastructure.client.parkinglot;

import com.parkmate.notificationservice.notification.infrastructure.client.parkinglot.response.ParkingLotAndSpotResponse;
import com.parkmate.notificationservice.notification.infrastructure.client.parkinglot.response.ParkingLotHostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ParkingLotClient {

    private final WebClient webClient;

//    private static final String BASE_URL = "http://localhost:8100/internal/parkingLots";
    private static final String BASE_URL = "http://PARKING-SERVICE";

    public CompletableFuture<ParkingLotAndSpotResponse> getParkingLotAndParkingSpotDetails(String parkingLotUuid, Long parkingSpotId) {
        return webClient.get()
                .uri(BASE_URL + "/internal/parkingLots/{parkingLotUuid}/parkingSpots/{parkingSpotId}", parkingLotUuid, parkingSpotId)
                .retrieve()
                .bodyToMono(ParkingLotAndSpotResponse.class)
                .toFuture();
    }

    public CompletableFuture<ParkingLotHostResponse> getParkingLotHostUuid(String parkingLotUuid) {
        return webClient.get()
                .uri(BASE_URL + "/internal/parkingLots/{parkingLotUuid}/host", parkingLotUuid)
                .retrieve()
                .bodyToMono(ParkingLotHostResponse.class)
                .toFuture();
    }
}
