package com.ikjo39.commerce.order.application;

import static com.ikjo39.commerce.common.type.ErrorCode.ALREADY_CANCELED_ORDER;
import static com.ikjo39.commerce.common.type.ErrorCode.ORDER_FAIL_CHECK_BASKET;

import com.ikjo39.commerce.common.exception.CustomException;
import com.ikjo39.commerce.common.type.ErrorCode;
import com.ikjo39.commerce.item.entity.OrderStatus;
import com.ikjo39.commerce.item.entity.ProductItem;
import com.ikjo39.commerce.item.service.ProductItemService;
import com.ikjo39.commerce.order.entity.OrderBasket;
import com.ikjo39.commerce.order.entity.redis.Basket;
import com.ikjo39.commerce.order.repository.OrderBasketRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderApplication {

	private final OrderBasketRepository orderBasketRepository;
	private final BasketApplication basketApplication;
	private final ProductItemService productItemService;

	@Transactional
	public OrderBasket orderBasket(Long memberId, Basket basket) {
		Basket orderBasket = basketApplication.refreshBasket(basket);
		if (orderBasket.getMessages().size() > 0) {
			throw new CustomException(ORDER_FAIL_CHECK_BASKET);
		}

		for (Basket.Product product : basket.getProducts()) {
			for (Basket.ProductItem basketItem : product.getItems()) {
				ProductItem productItem = productItemService.getProductItem(basketItem.getId());
				productItem.setAmount(productItem.getAmount() - basketItem.getAmount());
			}
		}
		OrderBasket order = OrderBasket.builder()
			.memberId(memberId)
			.orderStatus(OrderStatus.ORDERED)
			.totalPrice(totalPrice(orderBasket))
			.build();
		return orderBasketRepository.save(order);
	}

	@Transactional
	public OrderBasket orderCancelBasket(Long memberId, Basket basket, Long orderId) {
		Basket orderBasket = basketApplication.refreshBasket(basket);
		if (orderBasket.getMessages().size() > 0) {
			throw new CustomException(ORDER_FAIL_CHECK_BASKET);
		}
		OrderBasket order = orderBasketRepository.findById(orderId)
			.filter(ob -> ob.getMemberId().equals(memberId))
			.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
		if (order.getOrderStatus() == OrderStatus.CANCELED) {
			throw new CustomException(ALREADY_CANCELED_ORDER);
		}
		order.setOrderStatus(OrderStatus.CANCELED);
		order.setCancelDate(LocalDateTime.now());
		for (Basket.Product product : basket.getProducts()) {
			for (Basket.ProductItem basketItem : product.getItems()) {
				ProductItem productItem = productItemService.getProductItem(basketItem.getId());
				productItem.setAmount(productItem.getAmount() + basketItem.getAmount());
			}
		}
		return order;
	}

	private Long totalPrice(Basket basket) {
		long sum = 0;
		for (Basket.Product product : basket.getProducts()) {
			long price = product.getPrice();
			for (Basket.ProductItem item : product.getItems()) {
				sum += item.getAmount();
			}
			sum = price * sum;
		}
		return sum;
	}
}
