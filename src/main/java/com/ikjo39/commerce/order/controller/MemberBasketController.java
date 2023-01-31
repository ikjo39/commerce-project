package com.ikjo39.commerce.order.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikjo39.commerce.auth.config.JwtAuthenticationProvider;
import com.ikjo39.commerce.order.application.BasketApplication;
import com.ikjo39.commerce.order.application.OrderApplication;
import com.ikjo39.commerce.order.entity.redis.Basket;
import com.ikjo39.commerce.order.model.AddProductBasketForm;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member/basket")
@RequiredArgsConstructor
public class MemberBasketController {
	private final BasketApplication basketApplication;
	private final JwtAuthenticationProvider provider;
	private final OrderApplication orderApplication;

	@PostMapping
	public ResponseEntity<?> addBasket(
		@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody AddProductBasketForm form) {
		return ResponseEntity.ok(
			basketApplication.addBasket(provider.getUserVo(token).getId(), form));
	}

	@GetMapping
	public ResponseEntity<?> showBasket(
		@RequestHeader(name = "X-AUTH-TOKEN") String token) {
		return ResponseEntity.ok(basketApplication.getBasket(provider.getUserVo(token).getId()));
	}

	@GetMapping("/findBetween")
	public ResponseEntity<?> showBasketBetweenDate(
		@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
		@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		return ResponseEntity.ok(
			basketApplication.getBasketByLocalDate(
				provider.getUserVo(token).getId(), startDate, endDate
			)
		);
	}

	@PutMapping
	public ResponseEntity<?> updateBasket(
		@RequestHeader(name = "X-AUTH-TOKEN") String token, @RequestBody Basket basket) {
		return ResponseEntity.ok(
			basketApplication.updateBasket(provider.getUserVo(token).getId(), basket)
		);
	}

	@PostMapping("/order")
	public ResponseEntity<?> orderBasket(
		@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody Basket basket) {
		return ResponseEntity.ok(
			orderApplication.orderBasket(provider.getUserVo(token).getId(), basket));
	}

	@PutMapping("/order/cancel")
	public ResponseEntity<?> orderCancelBasket(
		@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody Basket basket, @RequestParam Long orderId) {
		return ResponseEntity.ok(
			orderApplication.orderCancelBasket(provider.getUserVo(token).getId(), basket, orderId));
	}
}
