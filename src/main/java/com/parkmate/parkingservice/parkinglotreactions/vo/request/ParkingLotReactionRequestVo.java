package com.parkmate.parkingservice.parkinglotreactions.vo.request;

import com.parkmate.parkingservice.parkinglotreactions.domain.ReactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotReactionRequestVo {

    private String parkingLotUuid;
    private String userUuid;
    private ReactionType reactionType;

    @Builder
    private ParkingLotReactionRequestVo(String parkingLotUuid,
                                        String userUuid,
                                        ReactionType reactionType) {
        this.parkingLotUuid = parkingLotUuid;
        this.userUuid = userUuid;
        this.reactionType = reactionType;
    }
}
