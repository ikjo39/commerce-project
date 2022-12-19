package com.ikjo39.commerce.admin.controller;

import com.ikjo39.commerce.item.dto.ProductDto;
import com.ikjo39.commerce.item.dto.ProductItemDto;
import com.ikjo39.commerce.item.entity.Product;
import com.ikjo39.commerce.item.model.AddProductForm;
import com.ikjo39.commerce.item.model.AddProductItemForm;
import com.ikjo39.commerce.item.model.UpdateProductForm;
import com.ikjo39.commerce.item.model.UpdateProductItemForm;
import com.ikjo39.commerce.item.service.ProductItemService;
import com.ikjo39.commerce.item.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/item")
@RequiredArgsConstructor
public class AdminProductController {

	private final ProductService productService;
	private final ProductItemService productItemService;

	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestBody AddProductForm form) {
		return ResponseEntity.ok(ProductDto.from(productService.addProduct(form)));
	}

	@PostMapping("/productItem")
	public ResponseEntity<?> addProductItem(@RequestBody AddProductItemForm form) {
		return ResponseEntity.ok(ProductDto.from(productItemService.addProductItem(form)));
	}

	@PutMapping("/product")
	public ResponseEntity<?> updateProduct(@RequestBody UpdateProductForm form) {
		return ResponseEntity.ok(ProductDto.from(productService.updateProduct(form)));
	}

	@PutMapping("/productItem")
	public ResponseEntity<?> updateProductItem(@RequestBody UpdateProductItemForm form) {
		return ResponseEntity.ok(ProductItemDto.from(productItemService.updateProductItem(form)));
	}

	@DeleteMapping("/product")
	public ResponseEntity<?> deleteProduct(@RequestParam Long id) {
		return ResponseEntity.ok(productService.deleteProduct(id));
	}

	@DeleteMapping("/productItem")
	public ResponseEntity<?> deleteProductItem(@RequestParam Long id) {
		return ResponseEntity.ok(productItemService.deleteProductItem(id));
	}

//	@GetMapping("/search")
//	public ResponseEntity<?> getAllProduct(@PageableDefault(size = 5, sort = "id", direction = Direction.DESC) Pageable pageable) {
//		return ResponseEntity.ok(productService.getAllProduct(pageable));
//	}
}