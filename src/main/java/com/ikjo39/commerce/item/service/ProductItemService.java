package com.ikjo39.commerce.item.service;

import static com.ikjo39.commerce.common.type.ErrorCode.ALREADY_IN_ITEM;
import static com.ikjo39.commerce.common.type.ErrorCode.PRODUCT_ITEM_NOT_FOUND;
import static com.ikjo39.commerce.common.type.ErrorCode.PRODUCT_NOT_FOUND;

import com.ikjo39.commerce.common.exception.CustomException;
import com.ikjo39.commerce.item.entity.Product;
import com.ikjo39.commerce.item.entity.ProductItem;
import com.ikjo39.commerce.item.model.AddProductItemForm;
import com.ikjo39.commerce.item.model.UpdateProductItemForm;
import com.ikjo39.commerce.item.repository.ProductItemRepository;
import com.ikjo39.commerce.item.repository.ProductRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductItemService {

	private final ProductRepository productRepository;
	private final ProductItemRepository productItemRepository;

	@Transactional
	public Product addProductItem(Long adminId, AddProductItemForm form) {
		Product product = productRepository.findByAdminIdAndId(adminId, form.getProductId())
			.orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
		if (product.getProductItems().stream()
			.anyMatch(item -> item.getName().equals(form.getName()))) {
			throw new CustomException(ALREADY_IN_ITEM);
		}
		ProductItem productItem = ProductItem.of(adminId, form);
		product.getProductItems().add(productItem);
		return product;
	}

	@Transactional
	public ProductItem updateProductItem(Long adminId, UpdateProductItemForm form) {
		ProductItem productItem = productItemRepository.findById(form.getId())
			.filter(pi -> pi.getAdminId().equals(adminId))
			.orElseThrow(() -> new CustomException(PRODUCT_ITEM_NOT_FOUND));
		productItem.setName(form.getName());
		productItem.setAmount(form.getAmount());
		return productItem;
	}

	@Transactional
	public String deleteProductItem(Long adminId, Long productItemId) {
		ProductItem productItem = productItemRepository.findById(productItemId)
			.filter(pi -> pi.getAdminId().equals(adminId))
			.orElseThrow(() -> new CustomException(PRODUCT_ITEM_NOT_FOUND));
		productItemRepository.delete(productItem);
		return productItem.getName();
	}
}
