package com.parkmate.notificationservice.notification.infrastructure.client.parking;

import com.parkmate.notificationservice.notification.infrastructure.client.response.ParkingLotHostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ParkingClient {

    private final WebClient webClient;

    private static final String BASE_URL = "http://PARKING-SERVICE";

    public CompletableFuture<ParkingLotHostResponse> getHostUuid(String parkingLotUuid) {
        return webClient.get()
                .uri(BASE_URL + "/internal/parkingLots/{parkingLotUuid}/host", parkingLotUuid)
                .retrieve()
                .bodyToMono(ParkingLotHostResponse.class)
                .toFuture();
    }
}
