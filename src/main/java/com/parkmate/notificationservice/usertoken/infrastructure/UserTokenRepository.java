package com.parkmate.notificationservice.usertoken.infrastructure;

import com.parkmate.notificationservice.usertoken.domain.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    UserToken findByUserUuid(String userUuid);
}
