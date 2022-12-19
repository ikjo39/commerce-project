package com.ikjo39.commerce.item.repository;

import com.ikjo39.commerce.item.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

	// ProductItem을 가져올 때는 먼저 가져오지 않고 일단 나뒀다가 필요할떄 가져옴
	// productItem 하나에 할당된 것들을 전부다 쿼리를 날려 가져옴
	@EntityGraph(attributePaths = {"productItems"}, type = EntityGraphType.LOAD)
	Optional<Product> findWithProductItemsById(Long id);
	@EntityGraph(attributePaths = {"productItems"}, type = EntityGraphType.LOAD)
	Optional<Product> findById(Long productId);

}
