package com.parkmate.parkingservice.parkingoperation.dto.request;

import com.parkmate.parkingservice.parkingoperation.vo.request.AmountRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AmountRequestDto {

    private String parkingLotUuid;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Builder
    private AmountRequestDto(String parkingLotUuid,
                            LocalDateTime startDateTime,
                            LocalDateTime endDateTime) {
        this.parkingLotUuid = parkingLotUuid;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static AmountRequestDto of(String parkingLotUuid, AmountRequestVo amountRequestVo) {
        return AmountRequestDto.builder()
                .parkingLotUuid(parkingLotUuid)
                .startDateTime(amountRequestVo.getStartDateTime())
                .endDateTime(amountRequestVo.getEndDateTime())
                .build();
    }
}
