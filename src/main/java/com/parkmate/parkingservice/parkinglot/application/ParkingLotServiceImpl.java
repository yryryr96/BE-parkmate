package com.parkmate.parkingservice.parkinglot.application;

import com.parkmate.parkingservice.common.exception.BaseException;
import com.parkmate.parkingservice.common.response.ResponseStatus;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotRegisterRequestDto;
import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotDeleteRequestDto;
import com.parkmate.parkingservice.parkinglot.dto.request.ParkingLotUpdateRequestDto;
import com.parkmate.parkingservice.parkinglot.dto.response.ParkingLotHostUuidResponseDto;
import com.parkmate.parkingservice.parkinglot.dto.response.ParkingLotResponseDto;
import com.parkmate.parkingservice.parkinglot.infrastructure.ParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    @Transactional
    @Override
    public ParkingLot register(ParkingLotRegisterRequestDto parkingLotRegisterRequestDto) {
        return parkingLotRepository.save(parkingLotRegisterRequestDto.toEntity());
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

    @Transactional
    @Override
    public void delete(ParkingLotDeleteRequestDto parkingLotDeleteRequestDto) {
        ParkingLot entity = parkingLotRepository.findByParkingLotUuidAndHostUuid(
                parkingLotDeleteRequestDto.getParkingLotUuid(),
                parkingLotDeleteRequestDto.getHostUuid()
        ).orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        parkingLotRepository.delete(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public ParkingLotResponseDto findByUuid(String parkingLotUuid) {
        return ParkingLotResponseDto.from(
                parkingLotRepository.findByParkingLotUuid(parkingLotUuid)
                        .orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public ParkingLotHostUuidResponseDto getHostUuidByParkingLotUuid(String parkingLotUuid) {
        return ParkingLotHostUuidResponseDto.from(
                parkingLotRepository.findHostUuidByParkingLotUuid(parkingLotUuid)
                        .orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND))
        );
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
                .mainAddress(entity.getMainAddress())
                .detailAddress(entity.getDetailAddress())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .parkingLotType(entity.getParkingLotType())
                .build();
    }
}
