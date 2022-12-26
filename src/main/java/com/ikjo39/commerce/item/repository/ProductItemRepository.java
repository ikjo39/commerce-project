package com.ikjo39.commerce.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ikjo39.commerce.item.entity.ProductItem;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
}
