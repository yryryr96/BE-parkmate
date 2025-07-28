package com.parkmate.parkingservice.kafka.event;

import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLotType;
import com.parkmate.parkingservice.parkinglotimagemapping.dto.response.ParkingLotImageMappingResponseDto;
import com.parkmate.parkingservice.parkinglotimagemapping.vo.Image;
import com.parkmate.parkingservice.parkinglotoption.dto.response.ParkingLotOptionResponseDto;
import com.parkmate.parkingservice.parkingspot.domain.EvChargeType;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
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
    private double latitude;
    private double longitude;
    private int capacity;
    private Boolean isEvChargingAvailable;
    private Set<EvChargeType> evChargeTypes;
    private String extraInfo;
    private String thumbnailUrl;
    private List<Image> imageUrls;
    private List<ParkingLotOptionResponseDto> options;
    private LocalDateTime timestamp;

    @Builder
    private ParkingLotCreatedEvent(String parkingLotUuid,
                                   String hostUuid,
                                   ParkingLotType parkingLotType,
                                   Set<ParkingSpotType> parkingSpotTypes,
                                   String name,
                                   String phoneNumber,
                                   String address,
                                   double latitude,
                                   double longitude,
                                   int capacity,
                                   Boolean isEvChargingAvailable,
                                   Set<EvChargeType> evChargeTypes,
                                   String extraInfo,
                                   String thumbnailUrl,
                                   List<Image> imageUrls,
                                   List<ParkingLotOptionResponseDto> options) {
        this.parkingLotUuid = parkingLotUuid;
        this.hostUuid = hostUuid;
        this.parkingLotType = parkingLotType;
        this.parkingSpotTypes = parkingSpotTypes;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
        this.isEvChargingAvailable = isEvChargingAvailable;
        this.evChargeTypes = evChargeTypes;
        this.extraInfo = extraInfo;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrls = imageUrls;
        this.options = options;
        this.timestamp = LocalDateTime.now();
    }

    public static ParkingLotCreatedEvent of(ParkingLot parkingLot,
                                            List<ParkingLotOptionResponseDto> options,
                                            List<ParkingSpotResponseDto> parkingSpots,
                                            List<ParkingLotImageMappingResponseDto> parkingLotImages) {


        Set<EvChargeType> evChargeTypes = parkingSpots.stream()
                .filter(spot -> spot.getType() == ParkingSpotType.EV)
                .map(ParkingSpotResponseDto::getEvChargeTypes)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        Set<ParkingSpotType> parkingSpotTypes = parkingSpots.stream()
                .map(ParkingSpotResponseDto::getType)
                .collect(Collectors.toSet());

        List<Image> sortedImages = parkingLotImages.stream()
                .sorted(Comparator.comparing(ParkingLotImageMappingResponseDto::getImageIndex))
                .map(dto -> new Image(dto.getImageUrl()))
                .toList();

        return ParkingLotCreatedEvent.builder()
                .parkingLotUuid(parkingLot.getParkingLotUuid())
                .hostUuid(parkingLot.getHostUuid())
                .parkingLotType(parkingLot.getParkingLotType())
                .phoneNumber(parkingLot.getPhoneNumber())
                .address(parkingLot.getMainAddress() + " " + parkingLot.getDetailAddress())
                .latitude(parkingLot.getLatitude())
                .longitude(parkingLot.getLongitude())
                .name(parkingLot.getName())
                .capacity(parkingLot.getRegisteredCapacity())
                .isEvChargingAvailable(parkingLot.getIsEvChargingAvailable())
                .extraInfo(parkingLot.getExtraInfo())
                .evChargeTypes(evChargeTypes)
                .parkingSpotTypes(parkingSpotTypes)
                .thumbnailUrl(parkingLot.getThumbnailUrl())
                .imageUrls(sortedImages)
                .options(options)
                .build();
    }
}
