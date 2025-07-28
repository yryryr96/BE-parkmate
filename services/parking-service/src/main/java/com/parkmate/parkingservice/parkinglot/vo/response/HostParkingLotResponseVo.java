package com.parkmate.parkingservice.parkinglot.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HostParkingLotResponseVo {

    private String parkingLotUuid;
    private String name;
    private String address;
    private String thumbnailUrl;
    private Boolean isOpen;

    @Builder
    private HostParkingLotResponseVo(String parkingLotUuid,
                                     String name,
                                     String address,
                                     String thumbnailUrl,
                                     Boolean isOpen) {
        this.parkingLotUuid = parkingLotUuid;
        this.name = name;
        this.address = address;
        this.thumbnailUrl = thumbnailUrl;
        this.isOpen = isOpen;
    }
}
