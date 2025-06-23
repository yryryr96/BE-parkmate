package com.parkmate.notificationservice.usertoken.application;

import com.parkmate.notificationservice.usertoken.domain.UserToken;
import com.parkmate.notificationservice.usertoken.dto.request.UserTokenSaveRequestDto;
import com.parkmate.notificationservice.usertoken.infrastructure.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {

    private final UserTokenRepository userTokenRepository;

    @Override
    public void saveToken(UserTokenSaveRequestDto userTokenSaveRequestDto) {
        userTokenRepository.save(userTokenSaveRequestDto.toEntity());
    }

    @Override
    public UserToken getTokenByUserUuid(String userUuid) {
        return userTokenRepository.findByUserUuid(userUuid);
    }
}
