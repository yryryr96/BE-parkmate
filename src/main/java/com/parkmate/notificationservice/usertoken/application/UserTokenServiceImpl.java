package com.parkmate.notificationservice.usertoken.application;

import com.parkmate.notificationservice.usertoken.domain.UserToken;
import com.parkmate.notificationservice.usertoken.dto.request.UserTokenSaveRequestDto;
import com.parkmate.notificationservice.usertoken.infrastructure.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {

    private final UserTokenRepository userTokenRepository;

    @Override
    public Mono<Void> saveToken(UserTokenSaveRequestDto userTokenSaveRequestDto) {
        return userTokenRepository.save(userTokenSaveRequestDto.toEntity()).then();
    }

    @Override
    public Mono<UserToken> getTokenByUserUuid(String userUuid) {
        return userTokenRepository.findByUserUuid(userUuid);
    }
}
