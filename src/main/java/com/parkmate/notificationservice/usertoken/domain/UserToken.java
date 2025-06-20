package com.parkmate.notificationservice.usertoken.domain;

import com.parkmate.notificationservice.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "user_token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserToken extends BaseEntity {

    @Id
    private Long id;

    @Column("user_uuid")
    private String userUuid;

    @Column("token")
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
