package com.parkmate.parkingreadservice.parkinglotread.dto.response;

import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import com.parkmate.parkingreadservice.parkinglotread.vo.response.ParkingLotSearchResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotSearchResponseDto {

    private String parkingLotUuid;
    private String name;
    private String address;
    private double latitude;
    private double longitude;

    @Builder
    private ParkingLotSearchResponseDto(String parkingLotUuid,
                                       String name,
                                       String address,
                                       double latitude,
                                       double longitude) {
        this.parkingLotUuid = parkingLotUuid;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static ParkingLotSearchResponseDto from(ParkingLotRead parkingLotRead) {
        return ParkingLotSearchResponseDto.builder()
                .parkingLotUuid(parkingLotRead.getParkingLotUuid())
                .name(parkingLotRead.getName())
                .address(parkingLotRead.getAddress())
                .latitude(parkingLotRead.getLatitude())
                .longitude(parkingLotRead.getLongitude())
                .build();
    }

    public ParkingLotSearchResponseVo toVo() {
        return ParkingLotSearchResponseVo.builder()
                .parkingLotUuid(parkingLotUuid)
                .name(name)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
