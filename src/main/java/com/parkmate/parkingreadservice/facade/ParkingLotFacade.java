package com.parkmate.parkingreadservice.facade;

import com.parkmate.parkingreadservice.common.utils.RedisUtil;
import com.parkmate.parkingreadservice.geo.application.GeoService;
import com.parkmate.parkingreadservice.geo.dto.request.InBoxParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.dto.request.NearbyParkingLotRequestDto;
import com.parkmate.parkingreadservice.geo.dto.response.GeoParkingLotResponseDto;
import com.parkmate.parkingreadservice.geo.dto.response.GeoParkingLotResponseDtoList;
import com.parkmate.parkingreadservice.geo.dto.response.ParkingLotsInRadiusResponse;
import com.parkmate.parkingreadservice.parkinglotread.application.ParkingLotReadService;
import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotReadResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingLotFacade {

    private final ParkingLotReadService parkingLotReadService;
    private final GeoService geoService;
    private final RedisUtil<String, ParkingLotReadResponseDto> redisUtil;

    public GeoParkingLotResponseDtoList getNearbyParkingLots(NearbyParkingLotRequestDto nearbyParkingLotRequestDto) {

        double latitude = nearbyParkingLotRequestDto.getLatitude();
        double longitude = nearbyParkingLotRequestDto.getLongitude();
        double radius = nearbyParkingLotRequestDto.getRadius();

        List<ParkingLotsInRadiusResponse> nearbyParkingLots = geoService.getNearbyParkingLots(latitude, longitude, radius);
        List<GeoParkingLotResponseDto> parkingLots = nearbyParkingLots.stream()
                .map(pl -> {
                    Optional<ParkingLotReadResponseDto> parkingLot = redisUtil.select(pl.getParkingLotUuid());
                    if (parkingLot.isPresent()) {
                        return parkingLot.get().toNearByParkingLotResponseDto(pl.getParkingLotUuid(), pl.getLatitude(), pl.getLongitude(), pl.getDistance());
                    }

                    ParkingLotReadResponseDto parkingLotReadResponseDto = parkingLotReadService.getParkingLotReadByParkingLotUuid(pl.getParkingLotUuid());
                    redisUtil.insert(pl.getParkingLotUuid(), parkingLotReadResponseDto);
                    return parkingLotReadResponseDto.toNearByParkingLotResponseDto(
                            pl.getParkingLotUuid(),
                            pl.getLatitude(),
                            pl.getLongitude(),
                            pl.getDistance()
                    );
                }).toList();

        return GeoParkingLotResponseDtoList.from(parkingLots);
    }

    public GeoParkingLotResponseDtoList getParkingLotBox(InBoxParkingLotRequestDto inBoxParkingLotRequestDto) {

        double swLat = inBoxParkingLotRequestDto.getSwLat();
        double swLng = inBoxParkingLotRequestDto.getSwLng();
        double neLat = inBoxParkingLotRequestDto.getNeLat();
        double neLng = inBoxParkingLotRequestDto.getNeLng();


    }
}
