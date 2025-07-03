package com.parkmate.parkingservice.parkingoperation.dto.response;

import com.parkmate.parkingservice.parkingoperation.vo.response.AmountResponseVo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AmountResponseDto {

    private long amount;

    private AmountResponseDto(long amount) {
        this.amount = amount;
    }

    public static AmountResponseDto from(long amount) {
        return new AmountResponseDto(amount);
    }

    public AmountResponseVo toVo() {
        return new AmountResponseVo(amount);
    }
}
