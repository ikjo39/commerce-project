package com.ikjo39.commerce.order.application;//package com.ikjo39.commerce.order.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ikjo39.commerce.admin.entity.Category;
import com.ikjo39.commerce.admin.model.CategoryRegisterForm;
import com.ikjo39.commerce.admin.service.AdminCategoryService;
import com.ikjo39.commerce.item.entity.Product;
import com.ikjo39.commerce.item.model.AddProductForm;
import com.ikjo39.commerce.item.model.AddProductItemForm;
import com.ikjo39.commerce.item.repository.ProductRepository;
import com.ikjo39.commerce.item.service.ProductService;
import com.ikjo39.commerce.order.entity.redis.Basket;
import com.ikjo39.commerce.order.model.AddProductBasketForm;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasketApplicationTest {

	@Autowired
	private BasketApplication basketApplication;

	@Autowired
	private ProductService productService;

	@Autowired
	private AdminCategoryService adminCategoryService;

	@Autowired
	private ProductRepository productRepository;

	@Test
	void ADD_TEST_MODIFY() {
		Long memberId = 100L;

		basketApplication.clearBasket(memberId);

		Product p = add_product();
		Product result = productRepository.findWithProductItemsById(p.getId()).get();

		assertNotNull(result);

		assertEquals(result.getName(), "나이키 에어포스");
		assertEquals(result.getCategoryName(), "신발");
		assertEquals(result.getPrice(), 10000L);

		assertEquals(result.getProductItems().size(), 3);
		assertEquals(result.getProductItems().get(0).getName(), "나이키 에어포스0");
		assertEquals(result.getProductItems().get(0).getAmount(), 10L);


		//TODO 데이터가 잘 들어갔는지
		Basket basket = basketApplication.addBasket(memberId, makeAddForm(result));
		assertEquals(basket.getMessages().size(), 0);

		basket = basketApplication.getBasket(memberId);
		assertEquals(basket.getMessages().size(), 1);
	}

	AddProductBasketForm makeAddForm(Product p) {
		AddProductBasketForm.ProductItem productItem = AddProductBasketForm.ProductItem.builder()
			.id(p.getProductItems().get(0).getId())
			.name(p.getProductItems().get(0).getName())
			.amount(5L)
			.build();

		return AddProductBasketForm.builder()
			.id(p.getId())
			.adminId(p.getAdminId())
			.categoryName(p.getCategoryName())
			.name(p.getName())
			.description(p.getDescription())
			.price(2000L)
			.items(List.of(productItem))
			.build();
	}

	Product add_product() {
		// given
		Long sellerId = 1L;
		// when
		CategoryRegisterForm categoryRegisterForm = makeCategoryForm("신발");
		Category category = adminCategoryService.register(sellerId, categoryRegisterForm);
		AddProductForm form = makeProductForm("나이키 에어포스", category.getCategoryName(), "한정판입니다.", 3);
		// then
		return productService.addProduct(sellerId, form);
	}


	private static CategoryRegisterForm makeCategoryForm(String categoryName) {
		return CategoryRegisterForm.builder()
			.categoryName(categoryName)
			.availability(true)
			.build();
	}


	private static AddProductForm makeProductForm(String name, String categoryName,
		String description, int count) {
		List<AddProductItemForm> itemForms = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			itemForms.add(
				makeProductItemForm(null, name + i)
			);
		}
		return AddProductForm.builder()
			.name(name)
			.categoryName(categoryName)
			.description(description)
			.image("이미지 링크")
			.price(10000L)
			.items(itemForms)
			.build();
	}

	private static AddProductItemForm makeProductItemForm(Long productId, String name) {
		return AddProductItemForm.builder()
			.productId(productId)
			.name(name)
			.amount(10L)
			.build();
	}
}