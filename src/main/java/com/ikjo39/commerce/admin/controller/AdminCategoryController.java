package com.ikjo39.commerce.admin.controller;

import com.ikjo39.commerce.admin.entity.Category;
import com.ikjo39.commerce.admin.model.CategoryRegisterForm;
import com.ikjo39.commerce.admin.service.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {

	private final AdminCategoryService adminCategoryService;

	@PostMapping("/register")
	public ResponseEntity<?> addCategory(@RequestBody CategoryRegisterForm form) {
		Category category = adminCategoryService.register(form);
		return ResponseEntity.ok(category);
	}

	@GetMapping
	public ResponseEntity<?> searchCategory(final Pageable pageable) {
		Page<Category> categories = adminCategoryService.getAllCategories(pageable);
		return ResponseEntity.ok(categories);
	}

//	@DeleteMapping("/{categoryName}")
//	public ResponseEntity<?> deleteCategory(@PathVariable String categoryName) {
//		String category = adminCategoryService.deleteCategory(categoryName);
//		return ResponseEntity.ok(category);
//	}

}
