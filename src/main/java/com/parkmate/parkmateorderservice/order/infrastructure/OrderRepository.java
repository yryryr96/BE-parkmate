package com.parkmate.parkmateorderservice.order.infrastructure;

import com.parkmate.parkmateorderservice.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderCode(String orderCode);
}
