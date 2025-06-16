package com.parkmate.parkingservice.parkinglotimagemapping.vo.request;

import com.parkmate.parkingservice.parkinglotimagemapping.vo.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ParkingLotImageRegisterRequestVo {

    private List<Image> imageUrls;

    @Builder
    private ParkingLotImageRegisterRequestVo(List<Image> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
