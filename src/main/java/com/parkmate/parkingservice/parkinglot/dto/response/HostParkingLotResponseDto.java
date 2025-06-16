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
    private String mainAddress;
    private String detailAddress;
    private String thumbnailUrl;

    @Builder
    private HostParkingLotResponseDto(String parkingLotUuid,
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

    public static HostParkingLotResponseDto from(ParkingLot parkingLot) {
        return HostParkingLotResponseDto.builder()
                .parkingLotUuid(parkingLot.getParkingLotUuid())
                .name(parkingLot.getName())
                .mainAddress(parkingLot.getMainAddress())
                .detailAddress(parkingLot.getDetailAddress())
                .thumbnailUrl(parkingLot.getThumbnailUrl())
                .build();
    }

    public HostParkingLotResponseVo toVo() {
        return HostParkingLotResponseVo.builder()
                .parkingLotUuid(parkingLotUuid)
                .name(name)
                .mainAddress(mainAddress)
                .detailAddress(detailAddress)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
