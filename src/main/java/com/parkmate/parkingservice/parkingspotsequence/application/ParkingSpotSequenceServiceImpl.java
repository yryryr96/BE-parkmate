package com.parkmate.parkingservice.parkingspotsequence.application;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
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
    public Long getSequenceBy(String parkingLotUuid,
                              ParkingSpotType parkingSpotType) {

        return parkingSpotSequenceRepository.findSequenceByParkingLotUuidAndParkingSpotType(
                parkingLotUuid,
                parkingSpotType
        );
    }
}
