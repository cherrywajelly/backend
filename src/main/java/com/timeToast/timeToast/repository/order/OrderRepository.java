package com.timeToast.timeToast.repository.order;

import com.timeToast.timeToast.domain.order.Order;

import java.util.Optional;
import java.util.List;

public interface OrderRepository {

    Order save(final Order order);
    Optional<Order> findById(final long orderId);
    List<Order> findAllByIconGroupId(final long iconGroupId);
}
