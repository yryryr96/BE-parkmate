package com.parkmate.parkingreadservice.parkinglotread.dto.response;

import lombok.Getter;

@Getter
public class ParkingLotSearchDto {

    private String id;
    private String parkingLotUuid;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private double score;
}
