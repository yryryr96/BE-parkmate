package com.parkmate.parking_service.parking_operation.application;

import com.parkmate.parking_service.parking_operation.dto.request.ParkingOperationCreateRequestDto;
import com.parkmate.parking_service.parking_operation.dto.request.ParkingOperationGetRequestDto;
import com.parkmate.parking_service.parking_operation.dto.request.ParkingOperationListGetRequestDto;
import com.parkmate.parking_service.parking_operation.dto.request.ParkingOperationUpdateRequestDto;
import com.parkmate.parking_service.parking_operation.dto.response.ParkingOperationResponseDto;

import java.util.List;

public interface ParkingOperationService {

    void register(ParkingOperationCreateRequestDto parkingOperationCreateRequestDto);

    List<ParkingOperationResponseDto> getParkingOperationList(ParkingOperationListGetRequestDto parkingOperationListGetRequestDto);

    ParkingOperationResponseDto getParkingOperation(ParkingOperationGetRequestDto parkingOperationGetRequestDto);

    void update(ParkingOperationUpdateRequestDto parkingOperationUpdateRequestDto);
}
