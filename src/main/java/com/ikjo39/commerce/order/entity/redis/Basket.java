package com.ikjo39.commerce.order.entity.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;

import com.ikjo39.commerce.order.model.AddProductBasketForm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@RedisHash("basket")
public class Basket {
	@Id
	private Long memberId;
	private List<Product> products = new ArrayList<>();
	private List<String> messages = new ArrayList<>();
	private String createdDate;

	public Basket(Long memberId) {
		this.memberId = memberId;
	}

	public void addMessage(String message) {
		messages.add(message);
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Product {
		private Long id;
		private Long adminId;
		private String categoryName;
		private String name;
		private String image;
		private String description;
		private Long price;
		private List<ProductItem> items = new ArrayList<>();

		public static Product from(AddProductBasketForm form) {
			return Product.builder()
				.id(form.getId())
				.adminId(form.getAdminId())
				.categoryName(form.getCategoryName())
				.name(form.getName())
				.image(form.getImage())
				.description(form.getDescription())
				.price(form.getPrice())
				.items(form.getItems().stream().map(ProductItem::from).collect(Collectors.toList()))
				.build();
		}
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ProductItem {
		private Long id;
		private String name;
		private Long amount;

		public static ProductItem from(AddProductBasketForm.ProductItem form) {
			return ProductItem.builder()
				.id(form.getId())
				.name(form.getName())
				.amount(form.getAmount())
				.build();
		}
	}
}
