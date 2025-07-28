package com.parkmate.userparkinghistoryservice.userpakinrghistory.application;

import com.parkmate.userparkinghistoryservice.userpakinrghistory.dto.request.EntryRequestDto;
import com.parkmate.userparkinghistoryservice.userpakinrghistory.dto.request.ExitRequestDto;

public interface UserParkingHistoryService {

    boolean requestEntry(EntryRequestDto entryRequestDto);

    boolean requestExit(ExitRequestDto exitRequestDto);
}
