package com.ikjo39.commerce.order.entity;

import com.ikjo39.commerce.common.entity.BaseEntity;
import com.ikjo39.commerce.item.entity.OrderStatus;
import java.time.LocalDateTime;
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

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class OrderBasket extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	private Long memberId;
	private Long totalPrice;
	private OrderStatus orderStatus;
	private LocalDateTime cancelDate;
}
