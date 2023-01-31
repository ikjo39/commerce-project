package com.ikjo39.commerce.item.entity;

import static com.ikjo39.commerce.item.entity.OrderStatus.DISPLAYED;

import com.ikjo39.commerce.common.entity.BaseEntity;
import com.ikjo39.commerce.item.model.AddProductItemForm;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@AuditOverride(forClass = BaseEntity.class)
public class ProductItem extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long adminId;
	@Audited
	private String name;
	@Audited
	private OrderStatus status;
	private Long amount;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public static ProductItem of(Long adminId, AddProductItemForm form) {
		return ProductItem.builder()
			.adminId(adminId)
			.name(form.getName())
			.status(DISPLAYED)
			.amount(form.getAmount())
			.build();
	}
}
