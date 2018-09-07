package com.test.recruitment.service;

import com.test.recruitment.json.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.recruitment.json.ErrorResponse;
import com.test.recruitment.exception.ServiceException;

/**
 * Exception handler
 * 
 * @author A525125
 *
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerService {

	/**
	 * Handles {@link ServiceException}
	 *
	 * @param e
	 *            exception
	 * @return error response
	 */
	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleServiceException(
			ServiceException e) {
		log.error("Error : " + e.getMessage());
		return ResponseEntity.status(e.getErrorCode().getHttpStatus().value())
				.body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
	}

	/**
	 * Handles {@link IllegalArgumentException} and {@link IllegalStateException}
	 *
	 * @param ex the exception entity
	 * @return error response
	 */
	@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
			RuntimeException ex) {
		log.error("Error : " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorResponse(ErrorCode.BAD_REQUEST, ex.getMessage()));
	}
}
