package com.ikjo39.commerce.admin.service;

import com.ikjo39.commerce.admin.entity.Category;
import com.ikjo39.commerce.common.exception.CustomException;
import com.ikjo39.commerce.admin.model.CategoryRegisterForm;
import com.ikjo39.commerce.admin.repository.CategoryRepository;
import com.ikjo39.commerce.item.repository.ProductItemRepository;
import com.ikjo39.commerce.common.type.ErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCategoryService {

	private final CategoryRepository categoryRepository;
	private final ProductItemRepository productItemRepository;

	public Category register(@RequestBody CategoryRegisterForm form) {
		Optional<Category> optionalCategory = categoryRepository.findByName(form.getName());
		if (optionalCategory.isPresent()) {
			throw new CustomException(ErrorCode.ALREADY_IN_CATEGORY);
		}
		Category category = Category.builder()
			.name(form.getName())
			.usingYn(form.isUsingYn())
			.build();
		return categoryRepository.save(category);
	}

	public Page<Category> getAllCategories(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

}
