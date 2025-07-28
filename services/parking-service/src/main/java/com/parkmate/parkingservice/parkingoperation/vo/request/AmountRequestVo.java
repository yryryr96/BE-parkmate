package com.parkmate.parkingservice.parkingoperation.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AmountRequestVo {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
