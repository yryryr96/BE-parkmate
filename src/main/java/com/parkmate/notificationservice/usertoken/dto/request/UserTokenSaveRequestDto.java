package com.parkmate.notificationservice.usertoken.dto.request;

import com.parkmate.notificationservice.usertoken.domain.UserToken;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserTokenSaveRequestDto {

    private String userUuid;
    private String token;

    @Builder
    private UserTokenSaveRequestDto(String userUuid, String token) {
        this.userUuid = userUuid;
        this.token = token;
    }

    public static UserTokenSaveRequestDto of(String userUuid, String token) {
        return UserTokenSaveRequestDto.builder()
                .userUuid(userUuid)
                .token(token)
                .build();
    }

    public UserToken toEntity() {
        return UserToken.builder()
                .userUuid(this.userUuid)
                .token(this.token)
                .build();
    }
}
