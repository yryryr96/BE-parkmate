package com.parkmate.reservationservice.reservation.infrastructure.repository;

import com.parkmate.reservationservice.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {

    Optional<Reservation> findByReservationCodeAndUserUuid(String reservationCode,
                                                           String userUuid);

    @Query(value = "SELECT r FROM Reservation r " +
            "WHERE r.parkingLotUuid = :parkingLotUuid and " +
            "r.status in ('CONFIRMED', 'IN_USE') and " +
            "r.entryTime < :exitTime and " +
            "r.exitTime > :entryTime"
    )
    List<Reservation> findAllByParkingLotUuid(String parkingLotUuid,
                                              LocalDateTime entryTime,
                                              LocalDateTime exitTime);

    Optional<Reservation> findByUserUuidAndReservationCodeAndParkingLotUuid(String userUuid,
                                                                       String reservationCode,
                                                                       String parkingLotUuid);

    Optional<Reservation> findByReservationCode(String reservationCode);
}
