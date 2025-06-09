package com.parkmate.parkingservice.parkinglotreactions.dto.request;

import com.parkmate.parkingservice.parkinglotreactions.domain.ParkingLotReactions;
import com.parkmate.parkingservice.parkinglotreactions.domain.ReactionType;
import com.parkmate.parkingservice.parkinglotreactions.vo.request.ParkingLotReactionUpsertRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotReactionUpsertRequestDto {

    private String parkingLotUuid;
    private String userUuid;
    private ReactionType reactionType;

    @Builder
    private ParkingLotReactionUpsertRequestDto(String parkingLotUuid,
                                               String userUuid,
                                               ReactionType reactionType) {
        this.parkingLotUuid = parkingLotUuid;
        this.userUuid = userUuid;
        this.reactionType = reactionType;
    }

    public static ParkingLotReactionUpsertRequestDto of(String parkingLotUuid,
                                                        String userUuid,
                                                        ParkingLotReactionUpsertRequestVo parkingLotReactionUpsertRequestVo) {
        return ParkingLotReactionUpsertRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .userUuid(userUuid)
                .reactionType(parkingLotReactionUpsertRequestVo.getReactionType())
                .build();
    }

    public ParkingLotReactions toEntity() {
        return ParkingLotReactions.builder()
                .parkingLotUuid(parkingLotUuid)
                .userUuid(userUuid)
                .reactionType(reactionType)
                .build();
    }
}
