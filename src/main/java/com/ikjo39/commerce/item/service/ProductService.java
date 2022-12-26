package com.ikjo39.commerce.item.service;

import static com.ikjo39.commerce.common.type.ErrorCode.CATEGORY_NOT_FOUND;
import static com.ikjo39.commerce.common.type.ErrorCode.PRODUCT_ITEM_NOT_FOUND;
import static com.ikjo39.commerce.common.type.ErrorCode.PRODUCT_NOT_FOUND;

import com.ikjo39.commerce.admin.entity.Category;
import com.ikjo39.commerce.admin.repository.CategoryRepository;
import com.ikjo39.commerce.common.exception.CustomException;
import com.ikjo39.commerce.item.entity.Product;
import com.ikjo39.commerce.item.entity.ProductItem;
import com.ikjo39.commerce.item.model.AddProductForm;
import com.ikjo39.commerce.item.model.UpdateProductForm;
import com.ikjo39.commerce.item.model.UpdateProductItemForm;
import com.ikjo39.commerce.item.repository.ProductRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	@Transactional
	public Product addProduct(Long adminId, AddProductForm form) {
		Category category = categoryRepository.findByCategoryName(form.getCategoryName())
			.orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND));
		return productRepository.save(Product.of(adminId, form, category.getCategoryName()));
	}

	@Transactional
	public Product updateProduct(Long adminId, UpdateProductForm form) {
		Category category = categoryRepository.findByCategoryName(form.getCategoryName())
			.orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND));
		Product product = productRepository.findByAdminIdAndId(adminId, form.getId())
			.orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
		product.setName(form.getName());
		product.setCategoryName(category.getCategoryName());
		product.setDescription(form.getDescription());
		product.setPrice(form.getPrice());
		product.setImage(form.getImage());
		for (UpdateProductItemForm itemForm : form.getItems()) {
			ProductItem item = product.getProductItems().stream()
				.filter(p1 -> p1.getId().equals(itemForm.getId()))
				.findFirst().orElseThrow(() -> new CustomException(PRODUCT_ITEM_NOT_FOUND));
			item.setName(itemForm.getName());
			item.setAmount(itemForm.getAmount());
		}
		return product;
	}

	@Transactional
	public String deleteProduct(Long adminId, Long productId) {
		Product product = productRepository.findByAdminIdAndId(adminId, productId)
			.orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
		productRepository.delete(product);
		return product.getName();
	}

	public Product getByProductId(Long productId) {
		return productRepository.findById(productId)
			.orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
	}

	public Page<Product> getAllProduct(Pageable pageable) {
		return productRepository.searchByPageable(pageable);
	}

	public List<Product> getListByProductIds(List<Long> productIds) {
		return productRepository.findAllByIdIn(productIds);
	}

	public List<Product> searchByName(String name) {
		return productRepository.searchByName(name);
	}

	public List<Product> searchByCategoryName(String categoryName) {
		return productRepository.searchByCategoryName(categoryName);
	}
}
