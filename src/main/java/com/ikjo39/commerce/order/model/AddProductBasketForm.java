package com.ikjo39.commerce.order.model;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProductBasketForm {

	private Long id;
	private Long adminId;
	private String categoryName;
	private String name;
	private String image;
	private String description;
	private Long price;
	private List<ProductItem> items;

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ProductItem {

		private Long id;
		private String name;
		private Long amount;
	}

}
