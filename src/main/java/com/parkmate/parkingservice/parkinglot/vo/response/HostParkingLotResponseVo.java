package com.parkmate.parkingservice.parkinglot.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HostParkingLotResponseVo {

    private String parkingLotUuid;
    private String name;
    private String mainAddress;
    private String detailAddress;
    private String thumbnailUrl;

    @Builder
    private HostParkingLotResponseVo(String parkingLotUuid,
                                    String name,
                                    String mainAddress,
                                    String detailAddress,
                                    String thumbnailUrl) {
        this.parkingLotUuid = parkingLotUuid;
        this.name = name;
        this.mainAddress = mainAddress;
        this.detailAddress = detailAddress;
        this.thumbnailUrl = thumbnailUrl;
    }
}
