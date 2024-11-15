package com.timeToast.timeToast.repository.order;

import com.timeToast.timeToast.domain.order.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    public OrderRepositoryImpl(final OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public Optional<Order> findById(final long orderId) {
        return orderJpaRepository.findById(orderId);
    }

    @Override
    public List<Order> findAllByIconGroupId(final long iconGroupId) {
        return orderJpaRepository.findAllByIconGroupId(iconGroupId);
    }
}
