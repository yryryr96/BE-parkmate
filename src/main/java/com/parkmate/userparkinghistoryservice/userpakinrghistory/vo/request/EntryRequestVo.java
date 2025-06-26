package com.parkmate.userparkinghistoryservice.userpakinrghistory.vo.request;

import lombok.Getter;

@Getter
public class EntryRequestVo {

    private String reservationCode;
    private String parkingLotUuid;
    private String vehicleNumber;
}
