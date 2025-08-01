package com.parkmate.notificationservice.usertoken.infrastructure;

import com.parkmate.notificationservice.usertoken.domain.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    List<UserToken> findByUserUuid(String userUuid);

    @Modifying
    @Query("DELETE FROM UserToken ut WHERE ut.token IN :tokens")
    void deleteAllInTokens(List<String> tokens);
}
