package com.parkmate.notificationservice.notification.infrastructure.client.user;

import com.parkmate.notificationservice.common.response.ApiResponse;
import com.parkmate.notificationservice.notification.infrastructure.client.user.response.UserNameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserClient {

    private final WebClient webClient;

//    private static final String BASE_URL = "http://USER-SERVICE";
    private static final String BASE_URL = "http://localhost:9200";
    private static final String USER_UUID_HEADER = "X-User-UUID";

    public CompletableFuture<ApiResponse<UserNameResponse>> getUserName(String userUuid) {
        return webClient.get()
                .uri(BASE_URL + "/api/v1/users/name")
                .header(USER_UUID_HEADER, userUuid)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<UserNameResponse>>() {})
                .toFuture();
    }
}
