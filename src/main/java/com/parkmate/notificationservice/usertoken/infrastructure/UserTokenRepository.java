package com.parkmate.notificationservice.usertoken.infrastructure;

import com.parkmate.notificationservice.usertoken.domain.UserToken;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserTokenRepository extends R2dbcRepository<UserToken, Long> {

    Mono<UserToken> findByUserUuid(String userUuid);
}
