package com.lockermat.config.web;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author Anton Gorokh
 */
@Log
@Controller
@ControllerAdvice
public class WebExceptionHandler extends ResponseEntityExceptionHandler implements ErrorController {

	@RequestMapping("/error")
	public void handleError(HttpServletRequest request) throws Throwable {
		throw (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class, UnsupportedOperationException.class})
	protected ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleHibernateConstraintViolation(org.hibernate.exception.ConstraintViolationException ex, WebRequest request) {
		SQLException sqlEx = ex.getSQLException();
		return handleExceptionInternal(ex, sqlEx.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
		log.log(Level.WARNING, ex.getMessage(), ex);
		return super.handleExceptionInternal(ex, body, headers, statusCode, request);
	}
}