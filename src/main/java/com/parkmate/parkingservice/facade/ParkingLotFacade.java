package com.parkmate.parkingservice.facade;

import com.parkmate.parkingservice.facade.dto.ParkingLotRegisterRequest;
import com.parkmate.parkingservice.kafka.event.ParkingLotCreatedEvent;
import com.parkmate.parkingservice.parkinglot.application.ParkingLotService;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkinglotimagemapping.application.ParkingLotImageMappingService;
import com.parkmate.parkingservice.parkinglotimagemapping.dto.response.ParkingLotImageMappingResponseDto;
import com.parkmate.parkingservice.parkinglotoption.application.ParkingLotOptionService;
import com.parkmate.parkingservice.parkinglotoption.dto.response.ParkingLotOptionResponseDto;
import com.parkmate.parkingservice.parkinglotoptionmapping.application.ParkingLotOptionMappingService;
import com.parkmate.parkingservice.parkinglotoptionmapping.dto.request.ParkingLotMappingUpdateRequestDto;
import com.parkmate.parkingservice.parkinglotoptionmapping.dto.response.ParkingLotOptionMappingResponseDto;
import com.parkmate.parkingservice.parkingspot.application.ParkingSpotService;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingLotFacade {

    private final ParkingLotService parkingLotService;
    private final ParkingSpotService parkingSpotService;
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
                        parkingSpotService.register(parkingSpot.withParkingLotUuid(parkingLotUuid))
                ).orElse(Collections.emptyList());

        List<ParkingLotImageMappingResponseDto> registeredImages = Optional.ofNullable(parkingLotRegisterRequest.getParkingLotImage())
                .map(parkingLotImage ->
                        parkingLotImageMappingService.registerParkingLotImages(parkingLotImage.withParkingLotUuid(parkingLotUuid))
                ).orElse(Collections.emptyList());

        eventPublisher.publishEvent(
                ParkingLotCreatedEvent.of(
                        parkingLot, options, registeredParkingSpots, registeredImages
                )
        );
    }
}
