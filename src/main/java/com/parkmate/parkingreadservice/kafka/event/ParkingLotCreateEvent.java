package com.parkmate.parkingreadservice.kafka.event;

import com.parkmate.parkingreadservice.parkinglotread.domain.Image;
import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParkingLotCreateEvent {

    private String parkingLotUuid;
    private String hostUuid;
    private String parkingLotType;
    private Set<String> parkingSpotTypes;
    private String name;
    private String phoneNumber;
    private String address;
    private double latitude;
    private double longitude;
    private int capacity;
    private Boolean isEvChargingAvailable;
    private Set<String> evChargeTypes;
    private String extraInfo;
    private String thumbnailUrl;
    private List<Image> imageUrls;
    private List<ParkingLotOption> options;
}
