package com.parkmate.parkingservice.parkinglotreactions.application;

import com.parkmate.parkingservice.parkinglotreactions.domain.ParkingLotReactions;
import com.parkmate.parkingservice.parkinglotreactions.domain.ReactionType;
import com.parkmate.parkingservice.parkinglotreactions.dto.request.ParkingLotReactionRequestDto;
import com.parkmate.parkingservice.parkinglotreactions.event.ReactionCreatedEvent;
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
    public void addReaction(ParkingLotReactionRequestDto parkingLotReactionRequestDto) {

        Optional<ParkingLotReactions> reaction = parkingLotReactionsRepository.findByParkingLotUuidAndUserUuid(
                parkingLotReactionRequestDto.getParkingLotUuid(),
                parkingLotReactionRequestDto.getUserUuid()
        );

        ReactionType previousReactionType = ReactionType.NONE;

        if (reaction.isEmpty()) {
            ParkingLotReactions newReaction = parkingLotReactionRequestDto.toEntity();
            parkingLotReactionsRepository.save(newReaction);

        } else {
            ParkingLotReactions existingReaction = reaction.get();
            previousReactionType = existingReaction.getReactionType();
            existingReaction.updateReactionType(parkingLotReactionRequestDto.getReactionType());
            parkingLotReactionsRepository.save(existingReaction);
        }

        eventPublisher.publishEvent(
                ReactionCreatedEvent.builder()
                        .parkingLotUuid(parkingLotReactionRequestDto.getParkingLotUuid())
                        .userUuid(parkingLotReactionRequestDto.getUserUuid())
                        .reactionType(parkingLotReactionRequestDto.getReactionType())
                        .previousReactionType(previousReactionType)
                        .build()
        );
    }
}
