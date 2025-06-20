package com.parkmate.notificationservice.usertoken.presentation;

import com.parkmate.notificationservice.usertoken.application.UserTokenService;
import com.parkmate.notificationservice.usertoken.domain.UserToken;
import com.parkmate.notificationservice.usertoken.dto.request.UserTokenSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usertokens")
public class UserTokenController {

    private final UserTokenService userTokenService;

    private final String USER_UUID_HEADER = "X-User-UUID";

    @PostMapping
    public Mono<Void> saveToken(@RequestHeader(USER_UUID_HEADER) String userUuid, @RequestParam String token) {
        return userTokenService.saveToken(UserTokenSaveRequestDto.of(userUuid, token));
    }

    @GetMapping
    public Mono<UserToken> getTokenByUserUuid(@RequestHeader(USER_UUID_HEADER) String userUuid) {
        return userTokenService.getTokenByUserUuid(userUuid);
    }
}
