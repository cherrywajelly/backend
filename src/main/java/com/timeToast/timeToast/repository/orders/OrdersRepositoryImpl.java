package com.timeToast.timeToast.repository.orders;

import com.timeToast.timeToast.domain.orders.Orders;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrdersRepositoryImpl implements OrdersRepository {

    private final OrdersJpaRepository orderJpaRepository;

    public OrdersRepositoryImpl(final OrdersJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public Orders save(Orders order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public Optional<Orders> findById(final long orderId) {
        return orderJpaRepository.findById(orderId);
    }

    @Override
    public List<Orders> findAllByIconGroupId(final long iconGroupId) {
        return orderJpaRepository.findAllByIconGroupId(iconGroupId);
    }
}
