package com.ikjo39.commerce.admin.controller;

import com.ikjo39.commerce.auth.config.JwtAuthenticationProvider;
import com.ikjo39.commerce.item.dto.ProductDto;
import com.ikjo39.commerce.item.dto.ProductItemDto;
import com.ikjo39.commerce.item.model.AddProductForm;
import com.ikjo39.commerce.item.model.AddProductItemForm;
import com.ikjo39.commerce.item.model.UpdateProductForm;
import com.ikjo39.commerce.item.model.UpdateProductItemForm;
import com.ikjo39.commerce.item.service.ProductItemService;
import com.ikjo39.commerce.item.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/item")
@RequiredArgsConstructor
public class AdminProductController {

	private final ProductService productService;
	private final ProductItemService productItemService;
	private final JwtAuthenticationProvider provider;

	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody AddProductForm form) {

		return ResponseEntity.ok(
			ProductDto.from(productService.addProduct(provider.getUserVo(token).getId(), form)));
	}

	@PostMapping("/productItem")
	public ResponseEntity<?> addProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody AddProductItemForm form) {
		return ResponseEntity.ok(
			ProductDto.from(
				productItemService.addProductItem(provider.getUserVo(token).getId(), form)));
	}

	@PutMapping("/product")
	public ResponseEntity<?> updateProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody UpdateProductForm form) {
		return ResponseEntity.ok(
			ProductDto.from(productService.updateProduct(provider.getUserVo(token).getId(), form)));
	}

	@PutMapping("/productItem")
	public ResponseEntity<?> updateProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody UpdateProductItemForm form) {
		return ResponseEntity.ok(ProductItemDto.from(
			productItemService.updateProductItem(provider.getUserVo(token).getId(), form)));
	}

	@DeleteMapping("/product")
	public ResponseEntity<?> deleteProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestParam Long id) {
		return ResponseEntity.ok(
			productService.deleteProduct(provider.getUserVo(token).getId(), id));
	}

	@DeleteMapping("/productItem")
	public ResponseEntity<?> deleteProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestParam Long id) {
		return ResponseEntity.ok(
			productItemService.deleteProductItem(provider.getUserVo(token).getId(), id));
	}
}