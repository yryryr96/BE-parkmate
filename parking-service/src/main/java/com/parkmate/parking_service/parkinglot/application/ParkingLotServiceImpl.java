package com.parkmate.parking_service.parkinglot.application;

import com.parkmate.parking_service.common.exception.BaseException;
import com.parkmate.parking_service.common.response.ResponseStatus;
import com.parkmate.parking_service.parkinglot.domain.ParkingLot;
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

    @Transactional
    @Override
    public void update(ParkingLotUpdateRequestDto parkingLotUpdateRequestDto) {

        ParkingLot entity = parkingLotRepository.findByParkingLotUuidAndHostUuid(
                parkingLotUpdateRequestDto.getParkingLotUuid(),
                parkingLotUpdateRequestDto.getHostUuid()
        ).orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        parkingLotRepository.save(createUpdatedParkingLotEntity(entity, parkingLotUpdateRequestDto));
    }

    @Override
    public void delete(ParkingLotDeleteRequestDto parkingLotDeleteRequestDto) {

    }

    @Override
    public ParkingLotResponseDto findById(String parkingLotUuid) {
        return null;
    }

    private ParkingLot createUpdatedParkingLotEntity(ParkingLot entity,
                                                     ParkingLotUpdateRequestDto parkingLotUpdateRequestDto) {
        return ParkingLot.builder()
                .id(entity.getId())
                .parkingLotUuid(entity.getParkingLotUuid())
                .hostUuid(parkingLotUpdateRequestDto.getHostUuid())
                .name(parkingLotUpdateRequestDto.getName())
                .phoneNumber(parkingLotUpdateRequestDto.getPhoneNumber())
                .capacity(parkingLotUpdateRequestDto.getCapacity())
                .registeredCapacity(parkingLotUpdateRequestDto.getRegisteredCapacity())
                .isEvChargingAvailable(parkingLotUpdateRequestDto.getIsEvChargingAvailable())
                .extraInfo(parkingLotUpdateRequestDto.getExtraInfo())
                .zoneCode(entity.getZoneCode())
                .mainAddress(entity.getMainAddress())
                .detailAddress(entity.getDetailAddress())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .parkingLotType(entity.getParkingLotType())
                .build();
    }
}
