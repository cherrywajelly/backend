package com.timeToast.timeToast.repository.orders;

import com.timeToast.timeToast.domain.orders.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersJpaRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAllByIconGroupId(final long iconGroupId);
}
