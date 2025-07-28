package com.parkmate.parkingreadservice.parkinglotread.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParkingLotSearchRequestVo {

    private String keyword;
    private String cursor;
    private Integer size;
}
