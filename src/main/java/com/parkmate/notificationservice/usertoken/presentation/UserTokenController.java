package com.parkmate.notificationservice.usertoken.presentation;

import com.parkmate.notificationservice.usertoken.application.UserTokenService;
import com.parkmate.notificationservice.usertoken.domain.UserToken;
import com.parkmate.notificationservice.usertoken.dto.request.UserTokenSaveRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usertokens")
public class UserTokenController {

    private final UserTokenService userTokenService;

    private final String USER_UUID_HEADER = "X-User-UUID";

    @Operation(
            summary = "사용자 fcm 토큰 저장",
            description = """
                    사용자 UUID와 fcm 토큰을 요청 헤더와 파라미터로 받아 fcm 토큰을 저장하는 API입니다.
                    """,
            tags = {"USER-TOKEN-SERVICE"}
    )
    @PostMapping
    public void saveToken(@RequestHeader(USER_UUID_HEADER) String userUuid,
                          @RequestParam String token) {
        userTokenService.saveToken(UserTokenSaveRequestDto.of(userUuid, token));
    }

    @Operation(
            summary = "사용자 UUID로 fcm 토큰 조회",
            description = """
                    사용자 UUID를 요청 헤더로 받아 해당 사용자의 fcm 토큰을 조회하는 API입니다.
                    """,
            tags = {"USER-TOKEN-SERVICE"}
    )
    @GetMapping
    public UserToken getTokenByUserUuid(@RequestHeader(USER_UUID_HEADER) String userUuid) {
        return userTokenService.getTokenByUserUuid(userUuid);
    }
}
