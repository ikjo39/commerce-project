package com.ikjo39.commerce.admin.repository;

import com.ikjo39.commerce.admin.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Optional<Category> findByCategoryName(String categoryName);

	Optional<Category> findByAdminIdAndCategoryId(Long adminId, Long categoryId);
}
