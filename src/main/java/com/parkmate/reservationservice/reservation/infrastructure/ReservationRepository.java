package com.parkmate.reservationservice.reservation.infrastructure;

import com.parkmate.reservationservice.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByReservationCodeAndUserUuid(String reservationCode,
                                                           String userUuid);
}
