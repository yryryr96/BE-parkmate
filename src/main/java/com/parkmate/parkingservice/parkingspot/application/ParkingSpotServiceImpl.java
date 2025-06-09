package com.parkmate.parkingservice.parkingspot.application;

import com.parkmate.parkingservice.common.exception.BaseException;
import com.parkmate.parkingservice.common.response.ResponseStatus;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpot;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotDeleteRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotGetRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotRegisterRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotUpdateRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotResponseDto;
import com.parkmate.parkingservice.parkingspot.infrastructure.ParkingSpotRepository;
import com.parkmate.parkingservice.parkingspot.vo.request.ChargeableSpotRegisterVo;
import com.parkmate.parkingservice.parkingspot.vo.request.NonChargeableSpotRegisterVo;
import com.parkmate.parkingservice.parkingspotsequence.application.ParkingSpotSequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ParkingSpotSequenceService parkingSpotSequenceService;

    private static final String EV_SPOT_NAME_PREFIX = "EV-";
    private static final String REGULAR_SPOT_NAME_PREFIX = "REG-";

    @Transactional
    @Override
    public List<ParkingSpotResponseDto> register(ParkingSpotRegisterRequestDto parkingSpotRegisterRequestDto) {

        List<ChargeableSpotRegisterVo> chargeable = parkingSpotRegisterRequestDto.getChargeable();
        List<NonChargeableSpotRegisterVo> nonChargeable = parkingSpotRegisterRequestDto.getNonChargeable();
        String parkingLotUuid = parkingSpotRegisterRequestDto.getParkingLotUuid();

        List<ParkingSpot> evParkingSpots = saveEvParkingSpots(parkingLotUuid, chargeable);
        List<ParkingSpot> regularParkingSpots = saveRegularParkingSpots(parkingLotUuid, nonChargeable);

        return Stream.of(evParkingSpots, regularParkingSpots)
                .flatMap(List::stream)
                .map(ParkingSpotResponseDto::from)
                .toList();
    }

    private List<ParkingSpot> saveRegularParkingSpots(String parkingLotUuid,
                                         List<NonChargeableSpotRegisterVo> nonChargeable) {

        if (nonChargeable == null || nonChargeable.isEmpty()) {
            return Collections.emptyList();
        }

        List<ParkingSpot> parkingSpots = new ArrayList<>();
        for (NonChargeableSpotRegisterVo nonChargeableSpotRegisterVo : nonChargeable) {

            ParkingSpotType parkingSpotType = nonChargeableSpotRegisterVo.getParkingSpotType();

            Long sequence = parkingSpotSequenceService.getSpotSequence(parkingLotUuid, parkingSpotType);
            for (int c = 0; c < nonChargeableSpotRegisterVo.getCount(); c++) {
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

        return parkingSpotRepository.saveAll(parkingSpots);
    }

    private List<ParkingSpot> saveEvParkingSpots(String parkingLotUuid,
                                    List<ChargeableSpotRegisterVo> chargeable) {

        if (chargeable == null || chargeable.isEmpty()) {
            return Collections.emptyList();
        }

        ParkingSpotType parkingSpotType = ParkingSpotType.EV;
        Long sequence = parkingSpotSequenceService.getSpotSequence(parkingLotUuid, parkingSpotType);
        List<ParkingSpot> parkingSpots = new ArrayList<>();

        for (ChargeableSpotRegisterVo chargeableSpotRegisterVo : chargeable) {
            ParkingSpot parkingSpot = ParkingSpot.builder()
                    .parkingLotUuid(parkingLotUuid)
                    .name(EV_SPOT_NAME_PREFIX + sequence)
                    .type(parkingSpotType)
                    .evChargeTypes(
                            new HashSet<>(chargeableSpotRegisterVo.getEvChargeTypes())
                    )
                    .build();

            parkingSpots.add(parkingSpot);
            sequence++;
        }

        parkingSpotSequenceService.update(parkingLotUuid, parkingSpotType, sequence);
        return parkingSpotRepository.saveAll(parkingSpots);
    }

    @Transactional
    @Override
    public void update(ParkingSpotUpdateRequestDto parkingSpotUpdateRequestDto) {
        parkingSpotRepository.save(
                parkingSpotUpdateRequestDto.toEntity()
        );
    }

    @Transactional
    @Override
    public void delete(ParkingSpotDeleteRequestDto parkingSpotDeleteRequestDto) {
        parkingSpotRepository.deleteByIdAndParkingLotUuid(
                parkingSpotDeleteRequestDto.getParkingSpotId(),
                parkingSpotDeleteRequestDto.getParkingLotUuid()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public ParkingSpotResponseDto getParkingSpot(ParkingSpotGetRequestDto parkingSpotGetRequestDto) {

        ParkingSpot entity = parkingSpotRepository.findByIdAndParkingLotUuid(
                parkingSpotGetRequestDto.getParkingSpotId(),
                parkingSpotGetRequestDto.getParkingLotUuid()
        ).orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        return ParkingSpotResponseDto.from(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParkingSpotResponseDto> getParkingSpots(String parkingLotUuid) {
        return parkingSpotRepository.findAllByParkingLotUuid(parkingLotUuid)
                .stream()
                .map(ParkingSpotResponseDto::from)
                .toList();
    }
}
