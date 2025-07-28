package com.parkmate.parkingservice.parkingoperation.vo.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AmountResponseVo {

    private long amount;

    public AmountResponseVo(long amount) {
        this.amount = amount;
    }
}
