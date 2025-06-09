package com.parkmate.parkingservice.parkinglotreactions.application;

import com.parkmate.parkingservice.parkinglotreactions.domain.ParkingLotReactions;
import com.parkmate.parkingservice.parkinglotreactions.domain.ReactionType;
import com.parkmate.parkingservice.parkinglotreactions.dto.request.ParkingLotReactionGetRequestDto;
import com.parkmate.parkingservice.parkinglotreactions.dto.request.ParkingLotReactionUpsertRequestDto;
import com.parkmate.parkingservice.kafka.event.ReactionUpdatedEvent;
import com.parkmate.parkingservice.parkinglotreactions.infrastructure.ParkingLotReactionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingLotReactionsServiceImpl implements ParkingLotReactionsService {

    private final ParkingLotReactionsRepository parkingLotReactionsRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public void addReaction(ParkingLotReactionUpsertRequestDto parkingLotReactionUpsertRequestDto) {

        Optional<ParkingLotReactions> reaction = parkingLotReactionsRepository.findByParkingLotUuidAndUserUuid(
                parkingLotReactionUpsertRequestDto.getParkingLotUuid(),
                parkingLotReactionUpsertRequestDto.getUserUuid()
        );

        ReactionType previousReactionType = ReactionType.NONE;

        if (reaction.isEmpty()) {
            ParkingLotReactions newReaction = parkingLotReactionUpsertRequestDto.toEntity();
            parkingLotReactionsRepository.save(newReaction);

        } else {
            ParkingLotReactions existingReaction = reaction.get();
            previousReactionType = existingReaction.getReactionType();
            existingReaction.updateReactionType(parkingLotReactionUpsertRequestDto.getReactionType());
            parkingLotReactionsRepository.save(existingReaction);
        }

        eventPublisher.publishEvent(
                ReactionUpdatedEvent.builder()
                        .parkingLotUuid(parkingLotReactionUpsertRequestDto.getParkingLotUuid())
                        .userUuid(parkingLotReactionUpsertRequestDto.getUserUuid())
                        .reactionType(parkingLotReactionUpsertRequestDto.getReactionType())
                        .previousReactionType(previousReactionType)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public ReactionType getReaction(ParkingLotReactionGetRequestDto parkingLotReactionGetRequestDto) {

        return parkingLotReactionsRepository.findReactionTypeByParkingLotUuidAndUserUuid(
                    parkingLotReactionGetRequestDto.getParkingLotUuid(),
                    parkingLotReactionGetRequestDto.getUserUuid()
                ).orElse(ReactionType.NONE);
    }
}
