package com.parkmate.notificationservice.usertoken.application;

import com.parkmate.notificationservice.usertoken.domain.UserToken;
import com.parkmate.notificationservice.usertoken.dto.request.UserTokenSaveRequestDto;

import java.util.List;

public interface UserTokenService {

    void saveToken(UserTokenSaveRequestDto userTokenSaveRequestDto);

    List<UserToken> getTokenByUserUuid(String userUuid);

    void deleteTokens(List<String> tokensToDelete);
}
