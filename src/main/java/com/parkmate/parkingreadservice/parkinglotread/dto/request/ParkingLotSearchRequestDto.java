package com.parkmate.parkingreadservice.parkinglotread.dto.request;

import com.parkmate.parkingreadservice.parkinglotread.vo.request.ParkingLotSearchRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingLotSearchRequestDto {

    private String keyword;
    private String cursor;
    private Integer size;

    @Builder
    private ParkingLotSearchRequestDto(String keyword,
                                      String cursor,
                                      Integer size) {
        this.keyword = keyword;
        this.cursor = cursor;
        this.size = size;
    }

    public static ParkingLotSearchRequestDto from(ParkingLotSearchRequestVo parkingLotSearchRequestVo) {
        return ParkingLotSearchRequestDto.builder()
                .keyword(parkingLotSearchRequestVo.getKeyword())
                .cursor(parkingLotSearchRequestVo.getCursor())
                .size(parkingLotSearchRequestVo.getSize())
                .build();
    }
}
