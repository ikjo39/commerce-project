package com.ikjo39.commerce.item.repository;

import com.ikjo39.commerce.item.dto.ProductDto;
import com.ikjo39.commerce.item.entity.Product;
import com.ikjo39.commerce.item.entity.QProduct;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Product> searchByName(String name) {
		String search = "%" + name + "%";
		QProduct qProduct = QProduct.product;
		return queryFactory.selectFrom(qProduct)
			.where(qProduct.name.like(search))
			.fetch();
	}

	@Override
	public List<Product> searchByCategoryName(String categoryName) {
		String search = "%" + categoryName + "%";
		QProduct qProduct = QProduct.product;
		return queryFactory.selectFrom(qProduct)
			.where(qProduct.categoryName.like(search))
			.fetch();
	}

	@Override
	public Page<Product> searchByPageable(Pageable pageable) {
		QProduct qProduct = QProduct.product;

		List<Product> results = queryFactory
			.selectFrom(qProduct)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return new PageImpl<>(results, pageable, results.size());
	}
}
