package com.parkmate.notificationservice.usertoken.domain;

import com.parkmate.notificationservice.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_uuid", nullable = false)
    private String userUuid;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Builder
    private UserToken(Long id,
                      String userUuid,
                      String token) {
        this.id = id;
        this.userUuid = userUuid;
        this.token = token;
    }
}
