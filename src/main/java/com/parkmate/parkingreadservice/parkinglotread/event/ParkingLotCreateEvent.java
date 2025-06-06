package com.parkmate.parkingreadservice.parkinglotread.event;

import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParkingLotCreateEvent {

    private String parkingLotUuid;
    private String hostUuid;
    private String parkingLotType; // PUBLIC, PRIVATE, COMMERCIAL 등
    private Set<String> parkingSpotTypes; // 소형, 중형, 대형, 경차 등
    private String name;
    private String phoneNumber;
    private String address;
    private int capacity;
    private Boolean isEvChargingAvailable;
    private Set<String> evChargeTypes;
    private String extraInfo;
    private List<String> imageUrls;

    public ParkingLotRead toEntity() {
        return ParkingLotRead.builder()
                .parkingLotUuid(parkingLotUuid)
                .hostUuid(hostUuid)
                .thumbnailUrl(imageUrls.isEmpty() ? null : imageUrls.get(0))
                .name(name)
                .phoneNumber(phoneNumber)
                .address(address)
                .isEvChargingAvailable(isEvChargingAvailable != null ? isEvChargingAvailable : false)
                .evChargeTypes(evChargeTypes)
                .parkingLotType(parkingLotType)
                .parkingSpotTypes(parkingSpotTypes)
                .extraInfo(extraInfo)
                .imageUrls(imageUrls)
                .likeCount(0)
                .dislikeCount(0)
                .build();
    }
}
