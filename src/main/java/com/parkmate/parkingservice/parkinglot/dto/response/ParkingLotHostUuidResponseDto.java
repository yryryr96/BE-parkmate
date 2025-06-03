package com.parkmate.parkingservice.parkinglot.dto.response;

import com.parkmate.parkingservice.parkinglot.vo.response.ParkingLotHostUuidResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotHostUuidResponseDto {

    private String hostUuid;

    @Builder
    private ParkingLotHostUuidResponseDto(String hostUuid) {
        this.hostUuid = hostUuid;
    }

    public static ParkingLotHostUuidResponseDto from(String hostUuid) {
        return ParkingLotHostUuidResponseDto.builder()
                .hostUuid(hostUuid)
                .build();
    }

    public ParkingLotHostUuidResponseVo toVo() {
        return ParkingLotHostUuidResponseVo.builder()
                .hostUuid(this.hostUuid)
                .build();
    }
}
