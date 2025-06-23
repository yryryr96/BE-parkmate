package com.parkmate.notificationservice.notification.infrastructure.client.reservation;

import com.parkmate.notificationservice.common.response.ApiResponse;
import com.parkmate.notificationservice.notification.infrastructure.client.reservation.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ReservationClient {

    private final WebClient webClient;

//    private static final String BASE_URL = "http://localhost:8200/api/v1";
    private static final String BASE_URL = "http://reservation-service";
    private static final String USER_UUID_HEADER = "X-User-UUID";

    public CompletableFuture<ApiResponse<ReservationResponse>> getReservationDetails(String reservationUuid, String userUuid) {

        return webClient.get()
                .uri(BASE_URL + "/api/v1/reservations/{reservationUuid}", reservationUuid)
                .header(USER_UUID_HEADER, userUuid)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<ReservationResponse>>() {})
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                new RuntimeException("API Call Failed after retries: " + retrySignal.failure().getMessage())))
                .toFuture();
    }
}
