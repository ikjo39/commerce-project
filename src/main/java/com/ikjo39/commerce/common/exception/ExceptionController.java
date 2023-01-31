package com.ikjo39.commerce.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ikjo39.commerce.common.type.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler({CustomException.class})
	public ResponseEntity<ExceptionResponse> customRequestException(final CustomException e) {
		log.warn("api Exception: {}", e.getErrorCode());
		return ResponseEntity.status(e.getStatusCode())
			.body(new ExceptionResponse(e.getStatusCode(), e.getMessage(), e.getErrorCode()));
	}

	@Getter
	@ToString
	@Builder
	@AllArgsConstructor
	public static class ExceptionResponse {
		private int status;
		private String message;
		private ErrorCode errorCode;
	}
}
