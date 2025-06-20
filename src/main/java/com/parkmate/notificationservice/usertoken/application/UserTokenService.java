package com.parkmate.notificationservice.usertoken.application;

import com.parkmate.notificationservice.usertoken.domain.UserToken;
import com.parkmate.notificationservice.usertoken.dto.request.UserTokenSaveRequestDto;
import reactor.core.publisher.Mono;

public interface UserTokenService {

    Mono<Void> saveToken(UserTokenSaveRequestDto userTokenSaveRequestDto);

    Mono<UserToken> getTokenByUserUuid(String userUuid);

}
