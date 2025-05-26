package com.parkmate.parking_service.parkinglot.application;

import com.parkmate.parking_service.parkinglot.dto.request.ParkingLotCreateRequestDto;
import com.parkmate.parking_service.parkinglot.dto.request.ParkingLotDeleteRequestDto;
import com.parkmate.parking_service.parkinglot.dto.request.ParkingLotUpdateRequestDto;
import com.parkmate.parking_service.parkinglot.dto.response.ParkingLotResponseDto;
import com.parkmate.parking_service.parkinglot.infrastructure.mysql.ParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    @Transactional
    @Override
    public void register(ParkingLotCreateRequestDto parkingLotCreateRequestDto) {
        parkingLotRepository.save(parkingLotCreateRequestDto.toEntity());
    }

    @Override
    public void update(ParkingLotUpdateRequestDto parkingLotUpdateRequestDto) {

        parkingLotRepository.findByParkingLotUuidAndHostUuid(
                parkingLotUpdateRequestDto.getParkingLotUuid(),
                parkingLotUpdateRequestDto.getHostUuid()
        ).orElseThrow(() -> new IllegalArgumentException("해당 주차장이 존재하지 않습니다."));

        parkingLotRepository.save(parkingLotUpdateRequestDto.toEntity());
    }

    @Override
    public void delete(ParkingLotDeleteRequestDto parkingLotDeleteRequestDto) {

    }

    @Override
    public ParkingLotResponseDto findById(String parkingLotUuid) {
        return null;
    }
}
