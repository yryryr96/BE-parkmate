package com.parkmate.parkingservice.parkingspot.domain;

import com.parkmate.parkingservice.parkingspot.vo.request.ChargeableSpotRegisterVo;
import com.parkmate.parkingservice.parkingspot.vo.request.NonChargeableSpotRegisterVo;
import com.parkmate.parkingservice.parkingspotsequence.application.ParkingSpotSequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ParkingSpotFactory {

    private static final String EV_SPOT_NAME_PREFIX = "EV-";
    private static final String REGULAR_SPOT_NAME_PREFIX = "REG-";

    private final ParkingSpotSequenceService parkingSpotSequenceService;

    public List<ParkingSpot> createEvSpots(String parkingLotUuid,
                                           List<ChargeableSpotRegisterVo> chargeable) {

        if (chargeable == null || chargeable.isEmpty()) {
            return Collections.emptyList();
        }

        ParkingSpotType parkingSpotType = ParkingSpotType.EV;
        Long sequence = parkingSpotSequenceService.getSpotSequence(parkingLotUuid, parkingSpotType);
        List<ParkingSpot> parkingSpots = new ArrayList<>();

        for (ChargeableSpotRegisterVo vo : chargeable) {
            ParkingSpot parkingSpot = ParkingSpot.builder()
                    .parkingLotUuid(parkingLotUuid)
                    .name(EV_SPOT_NAME_PREFIX + sequence)
                    .type(parkingSpotType)
                    .evChargeTypes(new HashSet<>(vo.getEvChargeTypes()))
                    .build();

            parkingSpots.add(parkingSpot);
            sequence++;
        }

        parkingSpotSequenceService.update(parkingLotUuid, parkingSpotType, sequence);
        return parkingSpots;
    }

    public List<ParkingSpot> createRegularSpots(String parkingLotUuid,
                                                List<NonChargeableSpotRegisterVo> nonChargeable) {

        if (nonChargeable == null || nonChargeable.isEmpty()) {
            return Collections.emptyList();
        }

        List<ParkingSpot> parkingSpots = new ArrayList<>();
        for (NonChargeableSpotRegisterVo vo : nonChargeable) {
            ParkingSpotType parkingSpotType = vo.getParkingSpotType();
            Long sequence = parkingSpotSequenceService.getSpotSequence(parkingLotUuid, parkingSpotType);

            for (int i = 0; i < vo.getCount(); i++) {
                ParkingSpot parkingSpot = ParkingSpot.builder()
                        .parkingLotUuid(parkingLotUuid)
                        .name(REGULAR_SPOT_NAME_PREFIX + parkingSpotType + sequence)
                        .type(parkingSpotType)
                        .evChargeTypes(null)
                        .build();

                parkingSpots.add(parkingSpot);
                sequence++;
            }
            parkingSpotSequenceService.update(parkingLotUuid, parkingSpotType, sequence);
        }

        return parkingSpots;
    }
}