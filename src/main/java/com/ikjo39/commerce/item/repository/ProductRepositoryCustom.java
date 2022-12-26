package com.ikjo39.commerce.item.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ikjo39.commerce.item.entity.Product;

public interface ProductRepositoryCustom {
	List<Product> searchByName(String name);

	List<Product> searchByCategoryName(String categoryName);

	Page<Product> searchByPageable(Pageable pageable);
}
