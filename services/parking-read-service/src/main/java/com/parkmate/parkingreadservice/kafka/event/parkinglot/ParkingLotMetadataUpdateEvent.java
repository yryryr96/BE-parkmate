package com.parkmate.parkingreadservice.kafka.event.parkinglot;

import com.parkmate.parkingreadservice.parkinglotread.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotMetadataUpdateEvent {

    private String parkingLotUuid;
    private Image thumbnailUrl;
    private String name;
    private String phoneNumber;
    private String extraInfo;
    private List<Image> imageUrls;
}
