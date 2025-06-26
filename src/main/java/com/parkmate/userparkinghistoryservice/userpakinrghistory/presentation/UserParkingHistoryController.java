package com.parkmate.userparkinghistoryservice.userpakinrghistory.presentation;

import com.parkmate.userparkinghistoryservice.common.response.ApiResponse;
import com.parkmate.userparkinghistoryservice.userpakinrghistory.application.UserParkingHistoryService;
import com.parkmate.userparkinghistoryservice.userpakinrghistory.dto.request.EntryRequestDto;
import com.parkmate.userparkinghistoryservice.userpakinrghistory.dto.request.ExitRequestDto;
import com.parkmate.userparkinghistoryservice.userpakinrghistory.vo.request.EntryRequestVo;
import com.parkmate.userparkinghistoryservice.userpakinrghistory.vo.request.ExitRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/userParkingHistory")
@RequiredArgsConstructor
public class UserParkingHistoryController {

    private final UserParkingHistoryService userParkingHistoryService;

    private static final String USER_UUID_HEADER = "X-USER-UUID";

    @PostMapping("/entry")
    public ApiResponse<Boolean> requestEntry(@RequestHeader(USER_UUID_HEADER) String userUuid,
                                             @RequestBody EntryRequestVo entryRequestVo) {

        return ApiResponse.ok(
                userParkingHistoryService.requestEntry(EntryRequestDto.of(userUuid, entryRequestVo))
        );
    }

    @PostMapping("/exit")
    public ApiResponse<Boolean> requestExit(@RequestHeader(USER_UUID_HEADER) String userUuid,
                                            @RequestBody ExitRequestVo exitRequestVo) {

        return ApiResponse.ok(
                userParkingHistoryService.requestExit(ExitRequestDto.of(userUuid, exitRequestVo))
        );
    }
}
