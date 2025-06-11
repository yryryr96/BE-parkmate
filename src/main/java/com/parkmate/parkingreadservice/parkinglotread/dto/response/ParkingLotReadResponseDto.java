package com.parkmate.parkingreadservice.parkinglotread.dto.response;

import com.parkmate.parkingreadservice.geo.dto.response.GeoParkingLotResponseDto;
import com.parkmate.parkingreadservice.parkinglotread.domain.Image;
import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotOption;
import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import com.parkmate.parkingreadservice.parkinglotread.vo.response.ParkingLotReadResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
public class ParkingLotReadResponseDto {

    private String hostUuid;
    private Image thumbnailUrl;
    private String name;
    private String phoneNumber;
    private String address;
    private String parkingLotType;
    private Set<String> parkingSpotTypes;
    private Boolean isEvChargingAvailable;
    private Set<String> evChargeTypes;
    private String extraInfo;
    private List<Image> imageUrls;
    private List<ParkingLotOption> options;
    private int likeCount;
    private int dislikeCount;

    @Builder
    private ParkingLotReadResponseDto(String hostUuid,
                                      Image thumbnailUrl,
                                      String name,
                                      String phoneNumber,
                                      String address,
                                      String parkingLotType,
                                      Set<String> parkingSpotTypes,
                                      Boolean isEvChargingAvailable,
                                      Set<String> evChargeTypes,
                                      String extraInfo,
                                      List<Image> imageUrls,
                                      List<ParkingLotOption> options,
                                      int likeCount,
                                      int dislikeCount) {
        this.hostUuid = hostUuid;
        this.thumbnailUrl = thumbnailUrl;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.parkingLotType = parkingLotType;
        this.parkingSpotTypes = parkingSpotTypes;
        this.isEvChargingAvailable = isEvChargingAvailable;
        this.evChargeTypes = evChargeTypes;
        this.extraInfo = extraInfo;
        this.imageUrls = imageUrls;
        this.options = options;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

    public static ParkingLotReadResponseDto from(ParkingLotRead parkingLotRead) {
        return ParkingLotReadResponseDto.builder()
                .hostUuid(parkingLotRead.getHostUuid())
                .thumbnailUrl(parkingLotRead.getThumbnailUrl())
                .name(parkingLotRead.getName())
                .phoneNumber(parkingLotRead.getPhoneNumber())
                .address(parkingLotRead.getAddress())
                .parkingLotType(parkingLotRead.getParkingLotType())
                .parkingSpotTypes(parkingLotRead.getParkingSpotTypes())
                .isEvChargingAvailable(parkingLotRead.getIsEvChargingAvailable())
                .evChargeTypes(parkingLotRead.getEvChargeTypes())
                .extraInfo(parkingLotRead.getExtraInfo())
                .imageUrls(parkingLotRead.getImageUrls())
                .options(parkingLotRead.getOptions())
                .likeCount(parkingLotRead.getLikeCount())
                .dislikeCount(parkingLotRead.getDislikeCount())
                .build();
    }

    public ParkingLotReadResponseVo toVo() {
        return ParkingLotReadResponseVo.builder()
                .hostUuid(hostUuid)
                .thumbnailUrl(thumbnailUrl)
                .name(name)
                .phoneNumber(phoneNumber)
                .address(address)
                .parkingLotType(parkingLotType)
                .parkingSpotTypes(parkingSpotTypes)
                .isEvChargingAvailable(isEvChargingAvailable)
                .evChargeTypes(evChargeTypes)
                .extraInfo(extraInfo)
                .imageUrls(imageUrls)
                .options(options)
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .build();
    }

    public GeoParkingLotResponseDto toGeoParkingLotResponseDto(String parkingLotUuid,
                                                               double latitude,
                                                               double longitude,
                                                               double distance) {
        return GeoParkingLotResponseDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .name(name)
//                .thumbnailUrl(thumbnailUrl.getImageUrl())
                .latitude(latitude)
                .longitude(longitude)
                .distance(distance)
                .build();
    }
}
