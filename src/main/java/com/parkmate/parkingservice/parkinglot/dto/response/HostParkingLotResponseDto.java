package com.parkmate.parkingservice.parkinglot.dto.response;

import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkinglot.vo.response.HostParkingLotResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HostParkingLotResponseDto {

    private String parkingLotUuid;
    private String name;
    private String address;
    private String thumbnailUrl;
    private Boolean isOpen;

    @Builder
    public HostParkingLotResponseDto(String parkingLotUuid,
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

    public static HostParkingLotResponseDto from(ParkingLot parkingLot, Boolean isOpen) {
        return HostParkingLotResponseDto.builder()
                .parkingLotUuid(parkingLot.getParkingLotUuid())
                .name(parkingLot.getName())
                .address(parkingLot.getMainAddress())
                .thumbnailUrl(parkingLot.getThumbnailUrl())
                .isOpen(isOpen)
                .build();
    }

    public HostParkingLotResponseVo toVo() {
        return HostParkingLotResponseVo.builder()
                .parkingLotUuid(parkingLotUuid)
                .name(name)
                .address(address)
                .thumbnailUrl(thumbnailUrl)
                .isOpen(isOpen)
                .build();
    }
}
