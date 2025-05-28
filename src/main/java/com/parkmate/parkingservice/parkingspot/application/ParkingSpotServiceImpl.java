package com.parkmate.parkingservice.parkingspot.application;

import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotDeleteRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotGetRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotRegisterRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotUpdateRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotResponseDto;
import com.parkmate.parkingservice.parkingspot.infrastructure.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    @Transactional
    @Override
    public void register(ParkingSpotRegisterRequestDto parkingSpotRegisterRequestDto) {

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

    @Override
    public ParkingSpotResponseDto getParkingSpot(ParkingSpotGetRequestDto parkingSpotGetRequestDto) {
        return null;
    }

    @Override
    public List<ParkingSpotResponseDto> getParkingSpots(String parkingLotUuid) {
        return List.of();
    }
}
