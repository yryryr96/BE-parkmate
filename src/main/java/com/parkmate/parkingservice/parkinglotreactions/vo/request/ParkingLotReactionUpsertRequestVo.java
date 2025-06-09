package com.parkmate.parkingservice.parkinglotreactions.vo.request;

import com.parkmate.parkingservice.parkinglotreactions.domain.ReactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotReactionUpsertRequestVo {

    private String userUuid;
    private ReactionType reactionType;

    @Builder
    private ParkingLotReactionUpsertRequestVo(String userUuid,
                                              ReactionType reactionType) {
        this.userUuid = userUuid;
        this.reactionType = reactionType;
    }
}
