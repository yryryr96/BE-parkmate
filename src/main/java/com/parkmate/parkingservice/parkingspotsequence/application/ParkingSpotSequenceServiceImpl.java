package com.parkmate.parkingservice.parkingspotsequence.application;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import com.parkmate.parkingservice.parkingspotsequence.domain.ParkingSpotSequence;
import com.parkmate.parkingservice.parkingspotsequence.infrastructure.ParkingSpotSequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingSpotSequenceServiceImpl implements ParkingSpotSequenceService {

    private final ParkingSpotSequenceRepository parkingSpotSequenceRepository;

    @Transactional(readOnly = true)
    @Override
    public Long getSpotSequence(String parkingLotUuid,
                                ParkingSpotType parkingSpotType) {

        return parkingSpotSequenceRepository.getRegularSpotSequence(
                        parkingLotUuid,
                        parkingSpotType)
                .orElse(1L);
    }

    @Transactional
    @Override
    public void update(String parkingLotUuid,
                       ParkingSpotType parkingSpotType,
                       Long sequence) {

        parkingSpotSequenceRepository.findByParkingLotUuidAndParkingSpotType(
                parkingLotUuid,
                parkingSpotType
        ).ifPresentOrElse(
                pss -> { pss.updateSequence(sequence); },
                () -> parkingSpotSequenceRepository.save(
                        ParkingSpotSequence.builder()
                                .parkingLotUuid(parkingLotUuid)
                                .parkingSpotType(parkingSpotType)
                                .sequence(sequence)
                                .build()
                ));
    }
}
