package com.mfc.auth.common.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mfc.auth.common.response.BaseResponse;
import com.mfc.auth.common.response.BaseResponseStatus;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<?> baseException(BaseException e) {
		BaseResponse<?> response = new BaseResponse<>(e.getStatus());
		log.info("e={}", e.getStatus());
		return new ResponseEntity<>(response, response.httpStatus());
	}

	@ExceptionHandler
	public ResponseEntity<?> incorrectPassword(BadCredentialsException e) { // 비밀번호 불일치
		BaseResponse<?> response = new BaseResponse<>(BaseResponseStatus.FAILED_TO_LOGIN);
		return new ResponseEntity<>(response, response.httpStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<BaseResponse<String>> processValidationError(MethodArgumentNotValidException e) {
		String errors = e.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
				.collect(Collectors.joining(", "));

		BaseResponse<String> response = new BaseResponse<>(
				HttpStatus.BAD_REQUEST, false, errors, 400, null);

		return new ResponseEntity<>(response, response.httpStatus());
	}

	// @ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleAllExceptions(Exception e) {
		BaseResponse<?> response = new BaseResponse<>(e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
