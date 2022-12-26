package com.ikjo39.commerce.item.controller;

import com.ikjo39.commerce.item.dto.ProductDto;
import com.ikjo39.commerce.item.model.ProductResult;
import com.ikjo39.commerce.item.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("/search/name")
	public ResponseEntity<?> searchProductByName(@RequestParam String name) {
		List<ProductDto> productDtos = productService.searchByName(name).stream()
			.map(ProductDto::withoutItemsfrom).toList();
		return ResponseEntity.ok(productDtos.stream().map(ProductResult::from)
			.collect(Collectors.toList()));
	}

	@GetMapping("/search/category")
	public ResponseEntity<?> searchProductByCategoryName(@RequestParam String categoryName) {
		List<ProductDto> productDtos = productService.searchByCategoryName(categoryName).stream()
			.map(ProductDto::withoutItemsfrom).toList();
		return ResponseEntity.ok(productDtos.stream().map(ProductResult::from)
			.collect(Collectors.toList()));
	}

	@GetMapping("/search/detail")
	public ResponseEntity<?> getDetail(@RequestParam Long productId) {
		return ResponseEntity.ok(ProductDto.from(productService.getByProductId(productId)));
	}
}
