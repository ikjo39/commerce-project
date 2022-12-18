package com.ikjo39.commerce.common.exception;

import com.ikjo39.commerce.common.type.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

	private final int statusCode;
	private final ErrorCode errorCode;

	public CustomException(ErrorCode errorCode) {
		super(errorCode.getDescription());
		this.statusCode = HttpStatus.BAD_REQUEST.value();
		this.errorCode = errorCode;
	}
}

