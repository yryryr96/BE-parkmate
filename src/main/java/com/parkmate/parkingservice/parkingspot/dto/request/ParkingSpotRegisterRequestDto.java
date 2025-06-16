package com.parkmate.parkingservice.parkingspot.dto.request;

import com.parkmate.parkingservice.parkingspot.vo.request.ChargeableSpotRegisterVo;
import com.parkmate.parkingservice.parkingspot.vo.request.NonChargeableSpotRegisterVo;
import com.parkmate.parkingservice.parkingspot.vo.request.ParkingSpotRegisterRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ParkingSpotRegisterRequestDto {

    private String parkingLotUuid;
    private List<ChargeableSpotRegisterVo> chargeable;
    private List<NonChargeableSpotRegisterVo> nonChargeable;

    @Builder
    private ParkingSpotRegisterRequestDto(String parkingLotUuid,
                                          List<ChargeableSpotRegisterVo> chargeable,
                                          List<NonChargeableSpotRegisterVo> nonChargeable) {

        this.parkingLotUuid = parkingLotUuid;
        this.chargeable = chargeable;
        this.nonChargeable = nonChargeable;
    }

    public static ParkingSpotRegisterRequestDto of(String parkingLotUuid,
                                                   ParkingSpotRegisterRequestVo parkingSpotRegisterRequestVo) {
        return ParkingSpotRegisterRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .chargeable(parkingSpotRegisterRequestVo.getChargeable())
                .nonChargeable(parkingSpotRegisterRequestVo.getNonChargeable())
                .build();
    }
}