package com.ikjo39.commerce.order.controller;

import com.ikjo39.commerce.auth.config.JwtAuthenticationProvider;
import com.ikjo39.commerce.order.application.BasketApplication;
import com.ikjo39.commerce.order.model.AddProductBasketForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member/basket")
@RequiredArgsConstructor
public class MemberBasketController {

	private final BasketApplication basketApplication;
	private final JwtAuthenticationProvider provider;

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
}
