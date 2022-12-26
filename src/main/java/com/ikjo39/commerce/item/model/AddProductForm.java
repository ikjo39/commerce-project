package com.ikjo39.commerce.item.model;


import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
// 목킹?
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddProductForm {
	@NotBlank(message = "이름은 필수 항목 합니다.")
	private String name;
	@NotBlank(message = "카테고리명은 필수 항목 합니다.")
	private String categoryName;
	@NotBlank(message = "상품 설명은 필수 항목 합니다.")
	private String description;
	@NotBlank(message = "이미지 링크 필수 항목 합니다.")
	private String image;
	private Long price;
	private List<AddProductItemForm> items;
}
