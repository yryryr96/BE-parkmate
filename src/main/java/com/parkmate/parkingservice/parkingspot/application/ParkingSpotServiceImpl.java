package com.parkmate.parkingservice.parkingspot.application;

import com.parkmate.parkingservice.common.exception.BaseException;
import com.parkmate.parkingservice.common.response.ResponseStatus;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpot;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotDeleteRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotGetRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotRegisterRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotUpdateRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotResponseDto;
import com.parkmate.parkingservice.parkingspot.infrastructure.ParkingSpotRepository;
import com.parkmate.parkingservice.parkingspotsequence.application.ParkingSpotSequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ParkingSpotSequenceService parkingSpotSequenceService;

    @Transactional
    @Override
    public void register(ParkingSpotRegisterRequestDto parkingSpotRegisterRequestDto) {

        Long sequence = parkingSpotSequenceService.getSequenceBy(parkingSpotRegisterRequestDto.getParkingLotUuid(),
                parkingSpotRegisterRequestDto.getType());

        System.out.println("sequence = " + sequence);
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
