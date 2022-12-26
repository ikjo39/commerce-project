package com.ikjo39.commerce.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	ALREADY_IN_ID("가입된 이메일이 존재합니다."),
	ALREADY_IN_CATEGORY("이미 등록된 카테고리가 존재합니다."),
	ALREADY_IN_ITEM("이미 등록된 아이템명이 존재합니다."),
	ALREADY_CANCELED_ORDER("이미 취소 처리된 주문입니다."),

	MEMBER_NOT_FOUND("회원정보가 없습니다."),
	INVALID_ID_OR_PASSWORD("아이디나 패스워드를 확인해주세요."),
	CATEGORY_NOT_FOUND("카테고리 정보가 없습니다."),
	PRODUCT_NOT_FOUND("상폼 정보가 없습니다."),
	PRODUCT_ITEM_NOT_FOUND("아이템 정보가 없습니다."),
	ORDER_NOT_FOUND("주문 정보가 없습니다."),

	FAIL_BASKET_CHANGE("장바구니에 추가할 수 없습니다."),
	ORDER_FAIL_CHECK_BASKET("주문불가! 장바구니를 확인해주세요."),
	NO_BASKET_SEARCHED("해당 일자 사이에 존재하는 장바구니가 없습니다."),
	NOT_ENOUGH_ITEM_AMOUNT("상품에 재고가 부족합니다."),
	PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다."),
	NEW_AND_RE_PASSWORD_NOT_MATCH("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
	private final String description;
}
