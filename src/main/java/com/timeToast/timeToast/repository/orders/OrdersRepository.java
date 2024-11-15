package com.timeToast.timeToast.repository.orders;

import com.timeToast.timeToast.domain.orders.Orders;

import java.util.Optional;
import java.util.List;

public interface OrdersRepository {

    Orders save(final Orders order);
    Optional<Orders> findById(final long orderId);
    List<Orders> findAllByIconGroupId(final long iconGroupId);
}
