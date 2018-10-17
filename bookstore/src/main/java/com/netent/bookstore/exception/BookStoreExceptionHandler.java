package com.netent.bookstore.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.netent.bookstore.response.BookStoreResponse;

@ControllerAdvice
@RestController
public class BookStoreExceptionHandler extends ResponseEntityExceptionHandler {

	private final MessageSource messageSource;

	@Autowired
	public BookStoreExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<BookStoreResponse> handleAllExceptions(Exception ex, WebRequest request,
			Locale locale) {
		String errorMsg = localeCheck(ex);
		BookStoreResponse bookStoreResponse = getBookStoreResponse(errorMsg,
				HttpStatus.INTERNAL_SERVER_ERROR.toString());
		return new ResponseEntity<>(bookStoreResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ BookStoreException.class })
	public final ResponseEntity<BookStoreResponse> handleBookStoreException(BookStoreException ex, WebRequest request,
			Locale locale) {
		String errorMsg = localeCheck(ex);
		BookStoreResponse bookStoreResponse = getBookStoreResponse(errorMsg, HttpStatus.NOT_ACCEPTABLE.toString());
		return new ResponseEntity<>(bookStoreResponse, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler({ ResourceNotFoundException.class })
	public final ResponseEntity<BookStoreResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
			WebRequest request, Locale locale) {
		String errorMsg = localeCheck(ex);
		BookStoreResponse bookStoreResponse = getBookStoreResponse(errorMsg, HttpStatus.NOT_FOUND.toString());
		return new ResponseEntity<>(bookStoreResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ ResourceAlreadyExistsException.class })
	public final ResponseEntity<BookStoreResponse> handleResourceAlreadyExistsException(
			ResourceAlreadyExistsException ex, WebRequest request, Locale locale) {
		String errorMsg = localeCheck(ex);
		BookStoreResponse bookStoreResponse = getBookStoreResponse(errorMsg, HttpStatus.CONFLICT.toString());
		return new ResponseEntity<>(bookStoreResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler({ BadRequestException.class })
	public final ResponseEntity<BookStoreResponse> handleBadRequestException(BadRequestException ex, WebRequest request,
			Locale locale) {
		String errorMsg = localeCheck(ex);
		BookStoreResponse bookStoreResponse = getBookStoreResponse(errorMsg, HttpStatus.BAD_REQUEST.toString());
		return new ResponseEntity<>(bookStoreResponse, HttpStatus.BAD_REQUEST);
	}

	private String localeCheck(Exception ex) {
		String message = null;
		try {
			message = messageSource.getMessage(ex.getMessage(), null, Locale.getDefault());
		} catch (NoSuchMessageException e) {
			return ex.getMessage();
		}
		return message;
	}

	private BookStoreResponse getBookStoreResponse(String errorMsg, String code) {
		BookStoreResponse bookStoreResponse = new BookStoreResponse();
		List<String> message = new ArrayList<>();
		message.add(errorMsg);
		bookStoreResponse.setMessages(message);
		bookStoreResponse.setCode(code);
		return bookStoreResponse;
	}
}
