package com.parkmate.parkingservice.parkinglotreactions.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "parking_lot_reactions",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_reactions_parking_lot_user",
                columnNames = {"parkingLotUuid", "userUuid"}
        )
)
public class ParkingLotReactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("주차장 UUID")
    @Column(length = 36, nullable = false)
    private String parkingLotUuid;

    @Comment("사용자 UUID")
    @Column(length = 36, nullable = false)
    private String userUuid;

    @Comment("리액션 타입")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReactionType reactionType = ReactionType.NONE;

    @Builder
    private ParkingLotReactions(Long id,
                               String parkingLotUuid,
                               String userUuid,
                               ReactionType reactionType) {
        this.id = id;
        this.parkingLotUuid = parkingLotUuid;
        this.userUuid = userUuid;
        this.reactionType = reactionType;
    }

    public void updateReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }
}
