package com.parkmate.parkingservice.parkingoperation.application;

import com.parkmate.parkingservice.parkingoperation.dto.request.ParkingOperationRegisterRequestDto;
import com.parkmate.parkingservice.parkingoperation.dto.request.ParkingOperationGetRequestDto;
import com.parkmate.parkingservice.parkingoperation.dto.request.ParkingOperationListGetRequestDto;
import com.parkmate.parkingservice.parkingoperation.dto.request.ParkingOperationUpdateRequestDto;
import com.parkmate.parkingservice.parkingoperation.dto.response.ParkingOperationResponseDto;

import java.util.List;

public interface ParkingOperationService {

    void register(ParkingOperationRegisterRequestDto parkingOperationRegisterRequestDto);

    List<ParkingOperationResponseDto> getParkingOperationList(ParkingOperationListGetRequestDto parkingOperationListGetRequestDto);

    ParkingOperationResponseDto getParkingOperation(ParkingOperationGetRequestDto parkingOperationGetRequestDto);

    void update(ParkingOperationUpdateRequestDto parkingOperationUpdateRequestDto);
}
