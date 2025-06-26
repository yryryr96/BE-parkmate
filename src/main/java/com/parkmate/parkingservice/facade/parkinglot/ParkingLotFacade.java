package com.parkmate.parkingservice.facade.parkinglot;

import com.parkmate.parkingservice.common.exception.BaseException;
import com.parkmate.parkingservice.common.response.ResponseStatus;
import com.parkmate.parkingservice.facade.parkinglot.dto.ParkingLotRegisterRequest;
import com.parkmate.parkingservice.kafka.event.ParkingLotCreatedEvent;
import com.parkmate.parkingservice.parkinglot.application.ParkingLotService;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkinglot.dto.response.HostParkingLotResponseDto;
import com.parkmate.parkingservice.parkinglot.dto.response.HostParkingLotResponseDtoList;
import com.parkmate.parkingservice.parkinglotimagemapping.application.ParkingLotImageMappingService;
import com.parkmate.parkingservice.parkinglotimagemapping.dto.request.ParkingLotImageRegisterRequestDto;
import com.parkmate.parkingservice.parkinglotimagemapping.dto.response.ParkingLotImageMappingResponseDto;
import com.parkmate.parkingservice.parkinglotoption.application.ParkingLotOptionService;
import com.parkmate.parkingservice.parkinglotoption.dto.response.ParkingLotOptionResponseDto;
import com.parkmate.parkingservice.parkinglotoptionmapping.application.ParkingLotOptionMappingService;
import com.parkmate.parkingservice.parkinglotoptionmapping.dto.request.ParkingLotMappingUpdateRequestDto;
import com.parkmate.parkingservice.parkinglotoptionmapping.dto.response.ParkingLotOptionMappingResponseDto;
import com.parkmate.parkingservice.parkingoperation.application.ParkingOperationService;
import com.parkmate.parkingservice.parkingspot.application.ParkingSpotService;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotRegisterRequestDto;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingLotFacade {

    private final ParkingLotService parkingLotService;
    private final ParkingSpotService parkingSpotService;
    private final ParkingOperationService parkingOperationService;
    private final ParkingLotOptionService parkingLotOptionService;
    private final ParkingLotOptionMappingService parkingLotOptionMappingService;
    private final ParkingLotImageMappingService parkingLotImageMappingService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void registerParkingLot(ParkingLotRegisterRequest parkingLotRegisterRequest) {

        ParkingLot parkingLot = parkingLotService.register(parkingLotRegisterRequest.getParkingLot());

        String parkingLotUuid = parkingLot.getParkingLotUuid();
        List<Long> optionIds = parkingLotRegisterRequest.getOptionIds();
        ParkingLotOptionMappingResponseDto registeredOptionMappings = parkingLotOptionMappingService.update(
                ParkingLotMappingUpdateRequestDto.of(parkingLotUuid, optionIds)
        );

        List<ParkingLotOptionResponseDto> options = parkingLotOptionService.getOptionsByOptionIds(
                registeredOptionMappings.getOptionIds()
        ).getOptions();

        List<ParkingSpotResponseDto> registeredParkingSpots = Optional.ofNullable(parkingLotRegisterRequest.getParkingSpot())
                .map(parkingSpot ->
                        parkingSpotService.register(ParkingSpotRegisterRequestDto.of(parkingLotUuid, parkingSpot))
                ).orElse(Collections.emptyList());

        List<ParkingLotImageMappingResponseDto> registeredImages = Optional.ofNullable(parkingLotRegisterRequest.getParkingLotImage())
                .map(parkingLotImage ->
                        parkingLotImageMappingService.registerParkingLotImages(ParkingLotImageRegisterRequestDto.of(
                                parkingLotUuid, parkingLotImage
                        ))
                ).orElse(Collections.emptyList());

        eventPublisher.publishEvent(
                ParkingLotCreatedEvent.of(
                        parkingLot, options, registeredParkingSpots, registeredImages
                )
        );
    }

    @Transactional(readOnly = true)
    public HostParkingLotResponseDtoList getHostParkingLots(String hostUuid) {

        List<ParkingLot> parkingLots = parkingLotService.getHostParkingLotsByHostUuid(hostUuid);

        if (parkingLots.isEmpty()) {
            throw new BaseException(ResponseStatus.RESOURCE_NOT_FOUND);
        }

        Map<String, Boolean> isOpenMap = parkingLots.stream()
                .collect(Collectors.toMap(ParkingLot::getParkingLotUuid, parkingLot -> false));

        List<String> openParkingLotUuids = parkingOperationService.getOpenParkingLotUuids(isOpenMap.keySet().stream().toList());
        for (String openParkingLotUuid : openParkingLotUuids) {
            isOpenMap.put(openParkingLotUuid, true);
        }

        System.out.println(openParkingLotUuids.size());

        List<HostParkingLotResponseDto> result = parkingLots.stream()
                .map(p -> HostParkingLotResponseDto.from(p, isOpenMap.get(p.getParkingLotUuid())))
                .toList();

        return HostParkingLotResponseDtoList.from(result);
    }
}
