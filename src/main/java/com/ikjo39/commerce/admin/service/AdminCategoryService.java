package com.ikjo39.commerce.admin.service;

import static com.ikjo39.commerce.common.type.ErrorCode.*;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ikjo39.commerce.admin.entity.Category;
import com.ikjo39.commerce.admin.model.CategoryRegisterForm;
import com.ikjo39.commerce.admin.repository.CategoryRepository;
import com.ikjo39.commerce.common.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCategoryService {
	private final CategoryRepository categoryRepository;

	public Category register(Long adminId, CategoryRegisterForm form) {
		Optional<Category> optionalCategory = categoryRepository.findByCategoryName(form.getCategoryName());
		if (optionalCategory.isPresent()) {
			throw new CustomException(ALREADY_IN_CATEGORY);
		}
		return categoryRepository.save(Category.of(adminId, form));
	}

	public Page<Category> getAllCategories(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	public String deleteCategory(Long adminId, Long categoryId) {
		Category category = categoryRepository.findByAdminIdAndCategoryId(adminId, categoryId)
			.orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND));
		categoryRepository.delete(category);
		return category.getCategoryName();
	}
}
