package com.ikjo39.commerce.item.dto;

import com.ikjo39.commerce.item.entity.Product;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	private Long id;
	private String categoryName;
	private String name;
	private String description;
	private String image;
	private Long price;
	private List<ProductItemDto> items;

	public static ProductDto from(Product product) {
		List<ProductItemDto> items = product.getProductItems()
			.stream().map(ProductItemDto::from).collect(Collectors.toList());
		return ProductDto.builder()
			.id(product.getId())
			.categoryName(product.getCategoryName())
			.name(product.getName())
			.description(product.getDescription())
			.price(product.getPrice())
			.image(product.getImage())
			.items(items)
			.build();
	}

	public static ProductDto withoutItemsfrom(Product product) {
		return ProductDto.builder()
			.id(product.getId())
			.categoryName(product.getCategoryName())
			.name(product.getName())
			.description(product.getDescription())
			.price(product.getPrice())
			.image(product.getImage())
			.build();
	}
}
