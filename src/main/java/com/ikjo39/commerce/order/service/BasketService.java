package com.ikjo39.commerce.order.service;

import com.ikjo39.commerce.order.client.RedisClient;
import com.ikjo39.commerce.order.entity.redis.Basket;
import com.ikjo39.commerce.order.model.AddProductBasketForm;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasketService {

	private final RedisClient redisClient;

	public Basket getBasket(Long memberId) {
		Basket basket = redisClient.get(memberId, Basket.class);
		return basket != null ? basket : new Basket();
	}

	public Basket putBasket(Long memberId, Basket basket) {
		redisClient.put(memberId, basket);
		return basket;
	}

	public Basket addBasket(Long memberId, AddProductBasketForm form) {
		Basket basket = redisClient.get(memberId, Basket.class);
		if (basket == null) {
			basket = new Basket();
			basket.setMemberId(memberId);
		}
		Optional<Basket.Product> optionalProduct = basket.getProducts().stream()
			.filter(product1 -> product1.getId().equals(form.getId()))
			.findFirst();
		if (optionalProduct.isPresent()) {
			Basket.Product redisProduct = optionalProduct.get();
			List<Basket.ProductItem> items = form.getItems().stream()
				.map(Basket.ProductItem::from).collect(Collectors.toList());
			Map<Long, Basket.ProductItem> redisItemMap = redisProduct.getItems().stream()
				.collect(Collectors.toMap(Basket.ProductItem::getId, item -> item));
			if (!redisProduct.getName().equals(form.getName())) {
				basket.addMessage(redisProduct.getName() + "의 정보가 변경되었습니다. 확인 부탁드립니다.");
			}
			if (!redisProduct.getPrice().equals(form.getPrice())) {
				basket.addMessage(redisProduct.getPrice() + "의 정보가 변경되었습니다. 확인 부탁드립니다..");
			}
			for (Basket.ProductItem item : items) {
				Basket.ProductItem redisItem = redisItemMap.get(item.getId());
				if (redisItem == null) {
					// happy Case
					redisProduct.getItems().add(item);
				} else {
					redisItem.setAmount(redisItem.getAmount() + item.getAmount());
				}
			}
		} else {
			Basket.Product product = Basket.Product.from(form);
			basket.getProducts().add(product);
		}
		redisClient.put(memberId, basket);
		return basket;
	}
}
