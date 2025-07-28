package com.parkmate.parkingservice.parkinglotreactions.application;

import com.parkmate.parkingservice.parkinglotreactions.domain.ReactionType;
import com.parkmate.parkingservice.parkinglotreactions.dto.request.ParkingLotReactionGetRequestDto;
import com.parkmate.parkingservice.parkinglotreactions.dto.request.ParkingLotReactionUpsertRequestDto;

public interface ParkingLotReactionsService {

    void addReaction(ParkingLotReactionUpsertRequestDto parkingLotReactionUpsertRequestDto);

    ReactionType getReaction(ParkingLotReactionGetRequestDto parkingLotReactionGetRequestDto);
}
