package com.parkmate.parkingreadservice.geo.dto.request;

import com.parkmate.parkingreadservice.geo.vo.request.InBoxParkingLotRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class InBoxParkingLotRequestDto {

    private double swLat;
    private double swLng;
    private double neLat;
    private double neLng;
    private boolean isEvChargingAvailable;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Builder
    private InBoxParkingLotRequestDto(double swLat,
                                      double swLng,
                                      double neLat,
                                      double neLng,
                                      boolean isEvChargingAvailable,
                                      LocalDateTime startDateTime,
                                      LocalDateTime endDateTime) {
        this.swLat = swLat;
        this.swLng = swLng;
        this.neLat = neLat;
        this.neLng = neLng;
        this.isEvChargingAvailable = isEvChargingAvailable;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static InBoxParkingLotRequestDto from(InBoxParkingLotRequestVo inBoxParkingLotRequestVo) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = inBoxParkingLotRequestVo.getStartDateTime() == null ? now : inBoxParkingLotRequestVo.getStartDateTime();
        LocalDateTime endDateTime = inBoxParkingLotRequestVo.getEndDateTime() == null ? LocalDateTime.of(now.toLocalDate(),
                LocalTime.of(23, 59,59)) : inBoxParkingLotRequestVo.getEndDateTime();

        return InBoxParkingLotRequestDto.builder()
                .swLat(inBoxParkingLotRequestVo.getSwLat())
                .swLng(inBoxParkingLotRequestVo.getSwLng())
                .neLat(inBoxParkingLotRequestVo.getNeLat())
                .neLng(inBoxParkingLotRequestVo.getNeLng())
                .isEvChargingAvailable(inBoxParkingLotRequestVo.isEvChargingAvailable())
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }
}
