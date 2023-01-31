package com.ikjo39.commerce.admin.dto;

import com.ikjo39.commerce.admin.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	private String name;

	public static CategoryDto from(Category category) {
		return CategoryDto.builder()
			.name(category.getName())
			.build();
	}
}
