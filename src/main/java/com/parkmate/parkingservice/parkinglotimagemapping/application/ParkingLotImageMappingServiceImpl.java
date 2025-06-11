package com.parkmate.parkingservice.parkinglotimagemapping.application;

import com.parkmate.parkingservice.parkinglotimagemapping.domain.ParkingLotImageMapping;
import com.parkmate.parkingservice.parkinglotimagemapping.dto.request.ParkingLotImageRegisterRequestDto;
import com.parkmate.parkingservice.parkinglotimagemapping.dto.response.ParkingLotImageMappingResponseDto;
import com.parkmate.parkingservice.parkinglotimagemapping.infrastructure.ParkingLotImageMappingRepository;
import com.parkmate.parkingservice.parkinglotimagemapping.vo.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotImageMappingServiceImpl implements ParkingLotImageMappingService {

    private final ParkingLotImageMappingRepository parkingLotImageMappingRepository;

    @Transactional
    @Override
    public List<ParkingLotImageMappingResponseDto> registerParkingLotImages(ParkingLotImageRegisterRequestDto parkingLotImageRegisterRequestDto) {

        if (parkingLotImageMappingRepository.existsByParkingLotUuid(parkingLotImageRegisterRequestDto.getParkingLotUuid())) {
            parkingLotImageMappingRepository.deleteByParkingLotUuid(parkingLotImageRegisterRequestDto.getParkingLotUuid());
        }

        String parkingLotUuid = parkingLotImageRegisterRequestDto.getParkingLotUuid();
        List<Image> imageUrls = parkingLotImageRegisterRequestDto.getImageUrls();
        List<ParkingLotImageMapping> parkingLotImageMappingList = new ArrayList<>();

        for (int idx = 0; idx < imageUrls.size(); idx++) {
            parkingLotImageMappingList.add(
                    ParkingLotImageMapping.builder()
                            .parkingLotUuid(parkingLotUuid)
                            .imageUrl(imageUrls.get(idx).getImageUrl())
                            .imageIndex(idx)
                            .build()
            );
        }

        List<ParkingLotImageMapping> parkingLotImageMappings = parkingLotImageMappingRepository.saveAll(parkingLotImageMappingList);
        return parkingLotImageMappings.stream()
                .map(ParkingLotImageMappingResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getImageUrlsByParkingLotUuid(String parkingLotUuid) {

        return parkingLotImageMappingRepository.findAllByParkingLotUuidOrderByImageIndex(parkingLotUuid)
                .stream()
                .map(ParkingLotImageMapping::getImageUrl)
                .toList();
    }
}
