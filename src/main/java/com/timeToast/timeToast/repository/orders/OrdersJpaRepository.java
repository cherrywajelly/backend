package com.timeToast.timeToast.repository.orders;

import com.timeToast.timeToast.domain.orders.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersJpaRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAllByIconGroupId(final long iconGroupId);
    List<Orders> findAllByIconGroupIdAndCreatedAtMonth(@Param("iconGroupId") Long iconGroupId, @Param("yearMonth") String yearMonth);
}
