package com.parkmate.notificationservice.usertoken.presentation;

import com.parkmate.notificationservice.usertoken.application.UserTokenService;
import com.parkmate.notificationservice.usertoken.domain.UserToken;
import com.parkmate.notificationservice.usertoken.dto.request.UserTokenSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usertokens")
public class UserTokenController {

    private final UserTokenService userTokenService;

    private final String USER_UUID_HEADER = "X-User-UUID";

    @PostMapping
    public void saveToken(@RequestHeader(USER_UUID_HEADER) String userUuid, @RequestParam String token) {
        userTokenService.saveToken(UserTokenSaveRequestDto.of(userUuid, token));
    }

    @GetMapping
    public UserToken getTokenByUserUuid(@RequestHeader(USER_UUID_HEADER) String userUuid) {
        return userTokenService.getTokenByUserUuid(userUuid);
    }
}
