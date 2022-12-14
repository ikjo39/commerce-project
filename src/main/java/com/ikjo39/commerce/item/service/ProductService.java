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
	public Product addProduct(AddProductForm form) {
		Category category = categoryRepository.findByName(form.getCategoryName())
			.orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND));
		return productRepository.save(Product.of(form, category.getName()));
	}

	@Transactional
	public Product updateProduct(UpdateProductForm form) {
		Category category = categoryRepository.findByName(form.getCategoryName())
			.orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND));
		Product product = productRepository.findById(form.getId())
			.orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
		product.setName(form.getName());
		product.setCategoryName(category.getName());
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
	public String deleteProduct(Long productId) {
		Product product = productRepository.findById(productId)
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

	public List<Product> searchByName(String name) {
		return productRepository.searchByName(name);
	}

	public List<Product> searchByCategoryName(String categoryName) {
		return productRepository.searchByCategoryName(categoryName);
	}
}
