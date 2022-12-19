package com.ikjo39.commerce.item.model;

import com.ikjo39.commerce.item.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResult {

	private Long id;
	private String categoryName;
	private String name;
	private String image;
	private Long price;

	public static ProductResult from(ProductDto productDto) {
		return ProductResult.builder()
			.id(productDto.getId())
			.categoryName(productDto.getCategoryName())
			.name(productDto.getName())
			.image(productDto.getImage())
			.price(productDto.getPrice())
			.build();
	}
}
