package com.netent.bookstore.exception;

public class BookStoreException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BookStoreException() {
		super();
	}

	public BookStoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public BookStoreException(String message) {
		super(message);
	}

	public BookStoreException(Throwable cause) {
		super(cause);
	}

}
