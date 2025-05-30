package com.parkmate.reservationservice.reservation.infrastructure;

import com.parkmate.reservationservice.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
