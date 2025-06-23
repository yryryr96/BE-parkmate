package com.parkmate.notificationservice.notification.infrastructure.client.reservation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ReservationResponse {

    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
}
