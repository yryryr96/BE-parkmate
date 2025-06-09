package com.parkmate.parkingservice.kafka.event;

import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLotType;
import com.parkmate.parkingservice.parkinglotimagemapping.dto.response.ParkingLotImageMappingResponseDto;
import com.parkmate.parkingservice.parkinglotimagemapping.vo.Image;
import com.parkmate.parkingservice.parkingspot.domain.EvChargeType;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ParkingLotCreatedEvent {

    private String parkingLotUuid;
    private String hostUuid;
    private ParkingLotType parkingLotType;
    private Set<ParkingSpotType> parkingSpotTypes;
    private String name;
    private String phoneNumber;
    private String address;
    private int capacity;
    private Boolean isEvChargingAvailable;
    private Set<EvChargeType> evChargeTypes;
    private String extraInfo;
    private List<Image> imageUrls;
    private LocalDateTime timestamp;

    @Builder
    private ParkingLotCreatedEvent(String parkingLotUuid,
                                   String hostUuid,
                                   ParkingLotType parkingLotType,
                                   Set<ParkingSpotType> parkingSpotTypes,
                                   String name,
                                   String phoneNumber,
                                   String address,
                                   int capacity,
                                   Boolean isEvChargingAvailable,
                                   Set<EvChargeType> evChargeTypes,
                                   String extraInfo,
                                   List<Image> imageUrls) {
        this.parkingLotUuid = parkingLotUuid;
        this.hostUuid = hostUuid;
        this.parkingLotType = parkingLotType;
        this.parkingSpotTypes = parkingSpotTypes;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.capacity = capacity;
        this.isEvChargingAvailable = isEvChargingAvailable;
        this.evChargeTypes = evChargeTypes;
        this.extraInfo = extraInfo;
        this.imageUrls = imageUrls;
        this.timestamp = LocalDateTime.now();
    }

    public static ParkingLotCreatedEvent of(ParkingLot parkingLot,
                                            List<ParkingSpotResponseDto> parkingSpots,
                                            List<ParkingLotImageMappingResponseDto> parkingLotImages) {

        Set<EvChargeType> evChargeTypes = new HashSet<>();
        Set<ParkingSpotType> parkingSpotTypes = new HashSet<>();
        List<Image> sortedImages = new ArrayList<>();

        if (parkingSpots != null) {

            evChargeTypes = parkingSpots.stream()
                    .filter(spot -> spot.getType() == ParkingSpotType.EV)
                    .map(ParkingSpotResponseDto::getEvChargeTypes)
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());

            parkingSpotTypes = parkingSpots.stream()
                    .map(ParkingSpotResponseDto::getType)
                    .collect(Collectors.toSet());
        }

        if (parkingLotImages != null) {
            sortedImages = parkingLotImages.stream()
                    .sorted(Comparator.comparing(ParkingLotImageMappingResponseDto::getImageIndex))
                    .map(dto -> new Image(dto.getImageUrl()))
                    .toList();
        }


        return ParkingLotCreatedEvent.builder()
                .parkingLotUuid(parkingLot.getParkingLotUuid())
                .hostUuid(parkingLot.getHostUuid())
                .parkingLotType(parkingLot.getParkingLotType())
                .phoneNumber(parkingLot.getPhoneNumber())
                .address(parkingLot.getMainAddress() + " " + parkingLot.getDetailAddress())
                .name(parkingLot.getName())
                .capacity(parkingLot.getCapacity())
                .isEvChargingAvailable(parkingLot.getIsEvChargingAvailable())
                .extraInfo(parkingLot.getExtraInfo())
                .evChargeTypes(evChargeTypes)
                .parkingSpotTypes(parkingSpotTypes)
                .imageUrls (sortedImages)
                .build();
    }
}
