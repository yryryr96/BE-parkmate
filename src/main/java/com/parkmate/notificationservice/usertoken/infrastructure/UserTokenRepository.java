package com.parkmate.notificationservice.usertoken.infrastructure;

import com.parkmate.notificationservice.usertoken.domain.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    List<UserToken> findByUserUuid(String userUuid);

    void deleteAllInTokens(List<String> tokens);
}
