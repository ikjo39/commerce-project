package com.ikjo39.commerce.item.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductItemForm {
	private Long id;
	private Long productId;
	@NotBlank(message = "이름은 필수 항목 합니다.")
	private String name;
	private Long amount;
}
