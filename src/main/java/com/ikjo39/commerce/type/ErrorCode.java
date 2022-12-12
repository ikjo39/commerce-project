package com.ikjo39.commerce.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	ALREADY_IN_ID("가입된 이메일이 존재합니다."),
	MEMBER_NOT_FOUND("회원정보가 없습니다.");
	private final String description;
}
