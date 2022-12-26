package com.ikjo39.commerce.item.dto;

import com.ikjo39.commerce.item.entity.OrderStatus;
import com.ikjo39.commerce.item.entity.ProductItem;
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
public class ProductItemDto {

	private Long id;
	private String name;
	private Long amount;

	public static ProductItemDto from(ProductItem item) {
		return ProductItemDto.builder()
			.id(item.getId())
			.name(item.getName())
			.amount(item.getAmount())
			.build();
	}
}
