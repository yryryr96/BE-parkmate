package com.parkmate.parkingservice.parkinglot.dto.response;

import com.parkmate.parkingservice.parkinglot.vo.response.ParkingLotHostUuidResponseVo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotHostUuidResponseDto {

    private String hostUuid;

    public ParkingLotHostUuidResponseDto(String hostUuid) {
        this.hostUuid = hostUuid;
    }

    public static ParkingLotHostUuidResponseDto from(String hostUuid) {
        return new ParkingLotHostUuidResponseDto(hostUuid);
    }

    public ParkingLotHostUuidResponseVo toVo() {
        return ParkingLotHostUuidResponseVo.builder()
                .hostUuid(hostUuid)
                .build();
    }
}
