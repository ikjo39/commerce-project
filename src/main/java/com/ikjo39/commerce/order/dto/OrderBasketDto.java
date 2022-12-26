package com.ikjo39.commerce.order.dto;

import java.time.LocalDateTime;

import com.ikjo39.commerce.item.entity.OrderStatus;
import com.ikjo39.commerce.order.entity.OrderBasket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBasketDto {
	private Long orderId;
	private Long memberId;
	private Long totalPrice;
	private OrderStatus orderStatus;
	private LocalDateTime cancelDate;

	public static OrderBasketDto from(OrderBasket order) {
		return OrderBasketDto.builder()
			.orderId(order.getOrderId())
			.memberId(order.getMemberId())
			.totalPrice(order.getTotalPrice())
			.orderStatus(OrderStatus.ORDERED)
			.build();
	}
}
