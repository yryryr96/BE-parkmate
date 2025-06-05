package com.parkmate.parkingreadservice.parkinglot.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotCreateEvent {

    private String parkingLotUuid;
    private String hostUuid;
    private String parkingLotType; // PUBLIC, PRIVATE, COMMERCIAL 등
    private Set<String> parkingSpotTypes; // 소형, 중형, 대형, 경차 등
    private String name;
    private String phoneNumber;
    private String address;
    private int capacity;
    private Boolean isEvChargingAvailable;
    private String extraInfo;
    private List<String> imageUrls;
}
