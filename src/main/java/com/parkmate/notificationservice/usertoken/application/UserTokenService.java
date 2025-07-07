package com.parkmate.notificationservice.usertoken.application;

import com.parkmate.notificationservice.usertoken.domain.UserToken;
import com.parkmate.notificationservice.usertoken.dto.request.UserTokenSaveRequestDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserTokenService {

    void saveToken(UserTokenSaveRequestDto userTokenSaveRequestDto);

    List<UserToken> getTokenByUserUuid(String userUuid);

    @Modifying
    @Query("DELETE FROM UserToken ut WHERE ut.token IN :tokens")
    void deleteTokens(List<String> tokens);
}
