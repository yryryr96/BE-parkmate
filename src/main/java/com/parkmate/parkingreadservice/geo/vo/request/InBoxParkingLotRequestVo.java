package com.parkmate.parkingreadservice.geo.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InBoxParkingLotRequestVo {

    private double swLat;
    private double swLng;
    private double neLat;
    private double neLng;
    private boolean isEvChargingAvailable;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
