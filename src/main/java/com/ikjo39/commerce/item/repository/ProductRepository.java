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

	@EntityGraph(attributePaths = {"productItems"}, type = EntityGraphType.LOAD)
	Optional<Product> findWithProductItemsById(Long id);

	@EntityGraph(attributePaths = {"productItems"}, type = EntityGraphType.LOAD)
	Optional<Product> findByAdminIdAndId(Long adminId, Long productId);

	@EntityGraph(attributePaths = {"productItems"}, type = EntityGraphType.LOAD)
	List<Product> findAllByIdIn(List<Long> ids);
}
