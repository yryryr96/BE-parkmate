package com.parkmate.parkingservice.parkinglotreactions.event;

import com.parkmate.parkingservice.parkinglotreactions.domain.ReactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReactionCreatedEvent {

    private String parkingLotUuid;
    private String userUuid;
    private ReactionType reactionType;
    private ReactionType previousReactionType;
    private LocalDateTime timestamp;

    @Builder
    private ReactionCreatedEvent(String parkingLotUuid,
                                String userUuid,
                                ReactionType reactionType,
                                ReactionType previousReactionType) {
        this.parkingLotUuid = parkingLotUuid;
        this.userUuid = userUuid;
        this.reactionType = reactionType;
        this.previousReactionType = previousReactionType;
        this.timestamp = LocalDateTime.now();
    }
}
