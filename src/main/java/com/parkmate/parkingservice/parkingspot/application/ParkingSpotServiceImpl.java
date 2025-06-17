package com.parkmate.parkingservice.parkingspot.application;

import com.parkmate.parkingservice.common.exception.BaseException;
import com.parkmate.parkingservice.common.response.ResponseStatus;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpot;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotFactory;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotDeleteRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotGetRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotRegisterRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotUpdateRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotResponseDto;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotSimpleResponseDto;
import com.parkmate.parkingservice.parkingspot.infrastructure.ParkingSpotRepository;
import com.parkmate.parkingservice.parkingspot.vo.request.ChargeableSpotRegisterVo;
import com.parkmate.parkingservice.parkingspot.vo.request.NonChargeableSpotRegisterVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ParkingSpotFactory parkingSpotFactory;

    @Transactional
    @Override
    public List<ParkingSpotResponseDto> register(ParkingSpotRegisterRequestDto parkingSpotRegisterRequestDto) {

        String parkingLotUuid = parkingSpotRegisterRequestDto.getParkingLotUuid();

        List<ChargeableSpotRegisterVo> chargeable = parkingSpotRegisterRequestDto.getChargeable();
        List<NonChargeableSpotRegisterVo> nonChargeable = parkingSpotRegisterRequestDto.getNonChargeable();

        List<ParkingSpot> evParkingSpots = parkingSpotFactory.createEvSpots(parkingLotUuid, chargeable);
        List<ParkingSpot> regularParkingSpots = parkingSpotFactory.createRegularSpots(parkingLotUuid, nonChargeable);

        List<ParkingSpot> allSpots = Stream.of(evParkingSpots, regularParkingSpots)
                .flatMap(List::stream)
                .toList();

        parkingSpotRepository.saveAll(allSpots);

        return allSpots.stream()
                .map(ParkingSpotResponseDto::from)
                .toList();
    }

    @Transactional
    @Override
    public void update(ParkingSpotUpdateRequestDto parkingSpotUpdateRequestDto) {
        parkingSpotRepository.save(parkingSpotUpdateRequestDto.toEntity());
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

        ParkingSpot parkingSpot = parkingSpotRepository.findByIdAndParkingLotUuid(
                parkingSpotGetRequestDto.getParkingSpotId(),
                parkingSpotGetRequestDto.getParkingLotUuid()
        ).orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        return ParkingSpotResponseDto.from(parkingSpot);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParkingSpotResponseDto> getParkingSpots(String parkingLotUuid) {
        return parkingSpotRepository.findAllByParkingLotUuid(parkingLotUuid)
                .stream()
                .map(ParkingSpotResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParkingSpotSimpleResponseDto> getParkingSpotsByParkingLotUuidAndType(String parkingLotUuid,
                                                                                     ParkingSpotType parkingSpotType) {

        return parkingSpotRepository.findAllByParkingLotUuidAndType(parkingLotUuid, parkingSpotType)
                .stream()
                .map(ParkingSpotSimpleResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ParkingSpot findById(Long id) {
        return parkingSpotRepository.findById(id).orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParkingSpot> findByIds(List<Long> parkingSpotIds) {
        return parkingSpotRepository.findALlByIdIn(parkingSpotIds);
    }
}
