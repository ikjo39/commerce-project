package com.ikjo39.commerce.item.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import com.ikjo39.commerce.common.entity.BaseEntity;
import com.ikjo39.commerce.item.model.AddProductForm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
@Entity
@AuditOverride(forClass = BaseEntity.class)
public class Product extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long adminId;
	private String categoryName;
	private String name;
	private String description;
	private String image;
	private Long price;
	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name = "product_id")
	private List<ProductItem> productItems = new ArrayList<>();

	public static Product of(Long adminId, AddProductForm form, String categoryName) {
		return Product.builder()
			.adminId(adminId)
			.categoryName(categoryName)
			.name(form.getName())
			.description(form.getDescription())
			.image(form.getImage())
			.price(form.getPrice())
			.productItems(
				form.getItems().stream().map(piForm -> ProductItem.of(adminId, piForm)).collect(Collectors.toList()))
			.build();
	}
}
