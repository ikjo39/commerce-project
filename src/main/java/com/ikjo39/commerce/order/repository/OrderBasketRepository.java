package com.ikjo39.commerce.order.repository;

import com.ikjo39.commerce.order.entity.OrderBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBasketRepository extends JpaRepository<OrderBasket, Long> {

}
