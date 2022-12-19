package com.ikjo39.commerce.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	ALREADY_IN_ID("가입된 이메일이 존재합니다."),
	ALREADY_IN_CATEGORY("이미 등록된 카테고리가 존재합니다."),
	ALREADY_IN_ITEM("이미 등록된 아이템명이 존재합니다."),

	MEMBER_NOT_FOUND("회원정보가 없습니다."),
	CATEGORY_NOT_FOUND("카테고리 정보가 없습니다."),
	PRODUCT_NOT_FOUND("상폼 정보가 없습니다."),
	PRODUCT_ITEM_NOT_FOUND("아이템 정보가 없습니다."),

	PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다."),
	NEW_AND_RE_PASSWORD_NOT_MATCH("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
	private final String description;
}
