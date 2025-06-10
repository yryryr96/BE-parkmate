package com.parkmate.parkingreadservice.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotReactionsUpdateEvent {

    private String parkingLotUuid;
    private ReactionType reactionType;
    private ReactionType previousReactionType;
    private LocalDateTime timestamp;
}
