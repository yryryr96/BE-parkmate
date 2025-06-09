package com.parkmate.parkingservice.parkinglotreactions.dto.request;

import com.parkmate.parkingservice.parkinglotreactions.domain.ParkingLotReactions;
import com.parkmate.parkingservice.parkinglotreactions.domain.ReactionType;
import com.parkmate.parkingservice.parkinglotreactions.vo.request.ParkingLotReactionRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotReactionRequestDto {

    private String parkingLotUuid;
    private String userUuid;
    private ReactionType reactionType;

    @Builder
    private ParkingLotReactionRequestDto(String parkingLotUuid,
                                         String userUuid,
                                         ReactionType reactionType) {
        this.parkingLotUuid = parkingLotUuid;
        this.userUuid = userUuid;
        this.reactionType = reactionType;
    }

    public static ParkingLotReactionRequestDto from(ParkingLotReactionRequestVo parkingLotReactionRequestVo) {
        return ParkingLotReactionRequestDto.builder()
                .parkingLotUuid(parkingLotReactionRequestVo.getParkingLotUuid())
                .userUuid(parkingLotReactionRequestVo.getUserUuid())
                .reactionType(parkingLotReactionRequestVo.getReactionType())
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
