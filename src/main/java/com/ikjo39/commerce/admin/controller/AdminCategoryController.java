package com.ikjo39.commerce.admin.controller;

import com.ikjo39.commerce.admin.dto.CategoryDto;
import com.ikjo39.commerce.admin.entity.Category;
import com.ikjo39.commerce.admin.model.CategoryRegisterForm;
import com.ikjo39.commerce.admin.service.AdminCategoryService;
import com.ikjo39.commerce.auth.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {

	private final AdminCategoryService adminCategoryService;
	private final JwtAuthenticationProvider provider;

	@PostMapping("/register")
	public ResponseEntity<?> addCategory(@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody CategoryRegisterForm form) {
		Category category = adminCategoryService.register(provider.getUserVo(token).getId(), form);
		return ResponseEntity.ok(CategoryDto.from(category));
	}

	@GetMapping
	public ResponseEntity<?> searchCategory(final Pageable pageable) {
		Page<Category> categories = adminCategoryService.getAllCategories(pageable);
		return ResponseEntity.ok(categories);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteCategory(@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestParam Long categoryId) {
		return ResponseEntity.ok(
			adminCategoryService.deleteCategory(provider.getUserVo(token).getId(), categoryId));
	}

}
