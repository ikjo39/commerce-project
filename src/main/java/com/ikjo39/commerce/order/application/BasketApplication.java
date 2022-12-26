package com.ikjo39.commerce.order.application;

import static com.ikjo39.commerce.common.type.ErrorCode.NOT_ENOUGH_ITEM_AMOUNT;
import static com.ikjo39.commerce.common.type.ErrorCode.NO_BASKET_SEARCHED;
import static com.ikjo39.commerce.common.type.ErrorCode.PRODUCT_NOT_FOUND;

import com.ikjo39.commerce.common.exception.CustomException;
import com.ikjo39.commerce.item.entity.Product;
import com.ikjo39.commerce.item.entity.ProductItem;
import com.ikjo39.commerce.item.service.ProductService;
import com.ikjo39.commerce.order.entity.redis.Basket;
import com.ikjo39.commerce.order.model.AddProductBasketForm;
import com.ikjo39.commerce.order.service.BasketService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasketApplication {

	private final ProductService productService;
	private final BasketService basketService;

	public Basket addBasket(Long memberId, AddProductBasketForm form) {
		Product product = productService.getByProductId(form.getId());
		if (product == null) {
			throw new CustomException(PRODUCT_NOT_FOUND);
		}
		Basket basket = basketService.getBasket(memberId);
		if (basket != null && !addAble(basket, product, form)) {
			throw new CustomException(NOT_ENOUGH_ITEM_AMOUNT);
		}
		return basketService.addBasket(memberId, form);
	}

	public Basket updateBasket(Long memberId, Basket basket) {
		basket.setCreatedDate(of(LocalDate.now()));
		basketService.putBasket(memberId, basket);
		return getBasket(memberId);
	}

	public Basket getBasket(Long memberId) {
		Basket basket = refreshBasket(basketService.getBasket(memberId));
		return getRefreshBasket(memberId, basket);
	}

	public Basket getBasketByLocalDate(Long memberId, LocalDate startDate, LocalDate endDate) {
		Basket basket = basketService.getBasket(memberId);
		if (!(beLocalDate(basket.getCreatedDate()).isAfter(startDate) &&
			beLocalDate(basket.getCreatedDate()).isBefore(endDate))) {
			throw new CustomException(NO_BASKET_SEARCHED);
		}
		return getRefreshBasket(memberId, basket);
	}

	private Basket getRefreshBasket(Long memberId, Basket basket) {
		basket = refreshBasket(basket);
		basketService.putBasket(basket.getMemberId(), basket);
		Basket returnBasket = new Basket();
		returnBasket.setMemberId(basket.getMemberId());
		returnBasket.setProducts(basket.getProducts());
		returnBasket.setMessages(basket.getMessages());
		returnBasket.setCreatedDate(basket.getCreatedDate());
		basket.setMessages(new ArrayList<>());
		basketService.putBasket(memberId, basket);
		return returnBasket;
	}

	public void clearBasket(Long memberId) {
		basketService.putBasket(memberId, null);
	}


	protected Basket refreshBasket(Basket basket) {
		Map<Long, Product> productMap = productService.getListByProductIds(
				basket.getProducts().stream().map(Basket.Product::getId).collect(
					Collectors.toList()))
			.stream()
			.collect(Collectors.toMap(Product::getId, product -> product));

		// TODO: 각각 케이스 에러가 정상 출력되는지 체크
		for (int i = 0; i < basket.getProducts().size(); i++) {
			Basket.Product basketProduct = basket.getProducts().get(i);
			boolean isPriceChanged = false;
			Product product = productMap.get(basketProduct.getId());
			if (product == null) {
				basket.getProducts().remove(basketProduct);
				i--;
				basket.addMessage(basketProduct.getName() + " 상품이 삭제되었습니다.");
				continue;
			}
			if (!product.getPrice().equals(productMap.get(product.getId()).getPrice())) {
				isPriceChanged = true;
				basketProduct.setPrice(product.getPrice());
			}
			Map<Long, ProductItem> productItemMap = product.getProductItems().stream()
				.collect(Collectors.toMap(ProductItem::getId, productItem -> productItem));

			List<String> tmpMessages = new ArrayList<>();
			for (int j = 0; j < basketProduct.getItems().size(); j++) {
				Basket.ProductItem basketProductItem = basketProduct.getItems().get(j);
				ProductItem pi = productItemMap.get(basketProductItem.getId());
				if (pi == null) {
					basketProduct.getItems().remove(basketProductItem);
					j--;
					tmpMessages.add(basketProductItem.getName() + " 옵션이 삭제되었습니다.");
					continue;
				}
				boolean isCountNotEnough = false;
				if (basketProductItem.getAmount() > productItemMap.get(basketProductItem.getId())
					.getAmount()) {
					isCountNotEnough = true;
					basketProductItem.setAmount(pi.getAmount());
				}
				if (isPriceChanged && isCountNotEnough) {
					tmpMessages.add(
						basketProductItem.getName() + " 가격변동, 수량 부족으로 구매 가능한 최대치로 변경되었습니다.");
				} else if (isPriceChanged) {
					tmpMessages.add(basketProductItem.getName() + " 가격이 변동되었습니다.");
				} else if (isCountNotEnough) {
					tmpMessages.add(basketProductItem.getName() + " 수량이 부족하여 구매 가능한 최대치로 변경되었습니다.");
				}
			}
			if (basketProduct.getItems().size() == 0) {
				basket.getProducts().remove(basketProduct);
				i--;
				basket.addMessage(basketProduct.getName() + " 상품의 옵션이 모두 없어져 구매가 불가능 합니다.");
				continue;
			} else if (tmpMessages.size() > 0) {
				StringBuilder builder = new StringBuilder();
				builder.append(basketProduct.getName() + "상품의 변동 사항 : ");
				for (String message : tmpMessages) {
					builder.append(message);
					builder.append(", ");
				}
				basket.addMessage(builder.toString());
			}
		}
		return basket;
	}

	private boolean addAble(Basket basket, Product product, AddProductBasketForm form) {
		Basket.Product basketProduct = basket.getProducts().stream()
			.filter(p -> p.getId().equals(form.getId()))
			.findFirst().orElse(Basket.Product.builder().id(product.getId())
				.items(Collections.emptyList()).build());
		Map<Long, Long> basketItemCountMap = basketProduct.getItems().stream()
			.collect(Collectors.toMap(Basket.ProductItem::getId, Basket.ProductItem::getAmount));
		Map<Long, Long> currentItemCountMap = product.getProductItems().stream()
			.collect(Collectors.toMap(ProductItem::getId, ProductItem::getAmount));
		return form.getItems().stream().noneMatch(
			formItem -> {
				Long basketCount = basketItemCountMap.get(formItem.getId());
				if (basketCount == null) {
					basketCount = 0L;
				}
				Long currentCount = currentItemCountMap.get(formItem.getId());
				return formItem.getAmount() + basketCount > currentCount;
			});
	}

	private String of(LocalDate date) {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	private LocalDate beLocalDate(String date) {
		return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
	}
}
