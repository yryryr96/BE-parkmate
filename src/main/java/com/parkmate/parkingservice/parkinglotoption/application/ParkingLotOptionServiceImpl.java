package com.parkmate.parkingservice.parkinglotoption.application;

import com.parkmate.parkingservice.parkinglotoption.domain.ParkingLotOption;
import com.parkmate.parkingservice.parkinglotoption.dto.request.ParkingLotOptionRegisterRequestDto;
import com.parkmate.parkingservice.parkinglotoption.dto.response.ParkingLotOptionResponseDto;
import com.parkmate.parkingservice.parkinglotoption.dto.response.ParkingLotOptionResponseDtoList;
import com.parkmate.parkingservice.parkinglotoption.infrastructure.ParkingLotOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingLotOptionServiceImpl implements ParkingLotOptionService {

    private final ParkingLotOptionRepository parkingLotOptionRepository;

    @Transactional
    @Override
    public ParkingLotOptionResponseDto registerOption(ParkingLotOptionRegisterRequestDto parkingLotOptionRegisterRequestDto) {
        ParkingLotOption savedOption = parkingLotOptionRepository.save(parkingLotOptionRegisterRequestDto.toEntity());
        return ParkingLotOptionResponseDto.from(savedOption);
    }

    @Transactional(readOnly = true)
    @Override
    public ParkingLotOptionResponseDtoList getOptions() {

        return ParkingLotOptionResponseDtoList.of(
                parkingLotOptionRepository.findAll().stream()
                        .map(ParkingLotOptionResponseDto::from)
                        .toList()
        );
    }
}
