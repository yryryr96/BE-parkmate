package com.parkmate.parkingservice.parkingoperation.application;

import com.parkmate.parkingservice.parkingoperation.dto.request.*;
import com.parkmate.parkingservice.parkingoperation.dto.response.ParkingOperationResponseDto;

import java.util.List;

public interface ParkingOperationService {

    void register(ParkingOperationCreateRequestDto parkingOperationCreateRequestDto);

    List<ParkingOperationResponseDto> getParkingOperationList(ParkingOperationListGetRequestDto parkingOperationListGetRequestDto);

    ParkingOperationResponseDto getParkingOperation(ParkingOperationGetRequestDto parkingOperationGetRequestDto);

    void update(ParkingOperationUpdateRequestDto parkingOperationUpdateRequestDto);

    void delete(ParkingOperationDeleteRequestDto parkingOperationDeleteRequestDto);
}
