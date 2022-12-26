package com.ikjo39.commerce.common.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikjo39.commerce.common.type.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CustomException extends RuntimeException {
	private final int statusCode;
	private final ErrorCode errorCode;
	private static final ObjectMapper mapper = new ObjectMapper();

	public CustomException(ErrorCode errorCode) {
		super(errorCode.getDescription());
		this.statusCode = HttpStatus.BAD_REQUEST.value();
		this.errorCode = errorCode;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CustomExceptionResponse {
		private int status;
		private String code;
		private String message;
	}
}

