package com.timeToast.timeToast.repository.order;

import com.timeToast.timeToast.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByIconGroupId(final long iconGroupId);
}
