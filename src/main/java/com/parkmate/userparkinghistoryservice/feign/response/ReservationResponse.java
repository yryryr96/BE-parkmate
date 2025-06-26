package com.parkmate.userparkinghistoryservice.feign.response;

import com.parkmate.userparkinghistoryservice.userpakinrghistory.dto.request.EntryRequestDto;
import com.parkmate.userparkinghistoryservice.userpakinrghistory.dto.request.ExitRequestDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {

    private String parkingLotUuid;
    private String userUuid;
    private String parkingSpotName;
    private String vehicleNumber;
    private String status;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public boolean canEnter(EntryRequestDto entryRequest) {

        final String validStatus = "CONFIRMED";

        if (!vehicleNumber.equals(entryRequest.getVehicleNumber())) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        return validStatus.equals(status) && now.isAfter(entryTime) && now.isBefore(exitTime);
    }

    public boolean canExit(ExitRequestDto exitRequestDto) {

        final String validStatus = "IN_USE";

        if (!vehicleNumber.equals(exitRequestDto.getVehicleNumber())) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        return validStatus.equals(status) && now.isAfter(entryTime) && now.isBefore(exitTime);
    }
}
