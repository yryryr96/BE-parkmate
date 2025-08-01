package com.parkmate.parkingservice.parkingoperation.application;

import com.parkmate.parkingservice.parkingoperation.dto.request.*;
import com.parkmate.parkingservice.parkingoperation.dto.response.AmountResponseDto;
import com.parkmate.parkingservice.parkingoperation.dto.response.ParkingOperationResponseDto;
import com.parkmate.parkingservice.parkingoperation.dto.response.WeeklyOperationResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ParkingOperationService {

    void register(ParkingOperationRegisterRequestDto parkingOperationCreateRequestDto);

    List<ParkingOperationResponseDto> getParkingOperationList(ParkingOperationListGetRequestDto parkingOperationListGetRequestDto);

    ParkingOperationResponseDto getParkingOperation(ParkingOperationGetRequestDto parkingOperationGetRequestDto);

    void update(ParkingOperationUpdateRequestDto parkingOperationUpdateRequestDto);

    void delete(ParkingOperationDeleteRequestDto parkingOperationDeleteRequestDto);

    List<WeeklyOperationResponseDto> getWeeklyOperations(String parkingLotUuid);

    ParkingOperationResponseDto getDailyOperation(String parkingLotUuid, LocalDate date);

    List<String> getOpenParkingLotUuids(List<String> parkingLotUuids);

    AmountResponseDto calculateTotalParkingAmount(AmountRequestDto amountRequestDto);
}
