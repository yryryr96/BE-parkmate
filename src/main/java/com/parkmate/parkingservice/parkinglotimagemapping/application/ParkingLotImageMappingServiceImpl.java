package com.parkmate.parkingservice.parkinglotimagemapping.application;

import com.parkmate.parkingservice.parkinglotimagemapping.domain.ParkingLotImageMapping;
import com.parkmate.parkingservice.parkinglotimagemapping.dto.request.ParkingLotImageRegisterRequestDto;
import com.parkmate.parkingservice.parkinglotimagemapping.infrastructure.ParkingLotImageMappingRepository;
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
    public void registerParkingLotImages(ParkingLotImageRegisterRequestDto parkingLotImageRegisterRequestDto) {

        if (parkingLotImageMappingRepository.existsByParkingLotUuid(parkingLotImageRegisterRequestDto.getParkingLotUuid())) {
            parkingLotImageMappingRepository.deleteByParkingLotUuid(parkingLotImageRegisterRequestDto.getParkingLotUuid());
        }

        String parkingLotUuid = parkingLotImageRegisterRequestDto.getParkingLotUuid();
        List<String> imageUrls = parkingLotImageRegisterRequestDto.getImageUrls();
        List<ParkingLotImageMapping> parkingLotImageMappingList = new ArrayList<>();

        for (int idx = 0; idx < imageUrls.size(); idx++) {
            parkingLotImageMappingList.add(
                    ParkingLotImageMapping.builder()
                            .parkingLotUuid(parkingLotUuid)
                            .imageUrl(imageUrls.get(idx))
                            .imageIndex(idx)
                            .build()
            );
        }

        parkingLotImageMappingRepository.saveAll(parkingLotImageMappingList);
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
