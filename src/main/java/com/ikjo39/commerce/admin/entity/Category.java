package com.ikjo39.commerce.admin.entity;

import com.ikjo39.commerce.admin.model.CategoryRegisterForm;
import com.ikjo39.commerce.common.entity.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CATEGORY")
@AuditOverride(forClass = BaseEntity.class)
public class Category extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
	private Long adminId;
	private String categoryName;
	private boolean availability;

	public static Category of(Long adminId, CategoryRegisterForm form) {
		return Category.builder()
			.adminId(adminId)
			.categoryName(form.getCategoryName())
			.availability(form.isAvailability())
			.build();
	}
}
