package com.ikjo39.commerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ikjo39.commerce.order.entity.OrderBasket;

@Repository
public interface OrderBasketRepository extends JpaRepository<OrderBasket, Long> {
}
