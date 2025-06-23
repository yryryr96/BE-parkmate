package com.parkmate.notificationservice.usertoken.application;

import com.parkmate.notificationservice.usertoken.domain.UserToken;
import com.parkmate.notificationservice.usertoken.dto.request.UserTokenSaveRequestDto;

public interface UserTokenService {

    void saveToken(UserTokenSaveRequestDto userTokenSaveRequestDto);

    UserToken getTokenByUserUuid(String userUuid);

}
