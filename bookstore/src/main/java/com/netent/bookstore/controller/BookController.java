package com.netent.bookstore.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netent.bookstore.dto.BookDto;
import com.netent.bookstore.dto.MediaPostDto;
import com.netent.bookstore.entities.Book;
import com.netent.bookstore.response.BookStoreResponse;
import com.netent.bookstore.services.BookService;

@RestController
@RequestMapping("/api/v1/bookstore/")
public class BookController {

	public static final Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookService bookService;

	@GetMapping("home")
	public ResponseEntity<BookStoreResponse> helloBookStore() {

		logger.info("BookController ::helloBookStore():: entry");
		BookDto bookDto = new BookDto(new Book());
		List<String> msgs = new ArrayList<>();
		msgs.add("Welcome to book store");
		BookStoreResponse response = new BookStoreResponse(HttpStatus.ACCEPTED.toString(), msgs, bookDto);
		logger.info("BookController ::helloBookStore():: exit");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping(value = "add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookStoreResponse> createBook(@RequestBody BookDto dto) {

		logger.info("BookController ::createBook():: entry");
		dto = bookService.createBook(dto);

		BookStoreResponse response = new BookStoreResponse(HttpStatus.CREATED.toString(), Collections.emptyList(), dto);
		logger.info("BookController ::createBook():: exit");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookStoreResponse> searchBook(@RequestParam(value = "isbn", required = false) String isbn,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "author", required = false) String author) {

		logger.info("BookController ::searchBook():: entry");
		List<BookDto> dtos = bookService.searchBook(new BookDto(isbn, title, author, null, null));

		List<String> msgs = new ArrayList<>();
		msgs.add("SUCCESS");

		BookStoreResponse response = new BookStoreResponse(HttpStatus.OK.toString(), msgs, dtos);
		logger.info("BookController ::searchBook():: exit");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(value = "mediacoverage/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookStoreResponse> mediaCoverage(@PathVariable(value = "isbn", required = true) String isbn) {

		logger.info("BookController ::mediaCoverage():: entry");
		List<MediaPostDto> dtos = bookService.searchMediaCoverageByIsbn(new BookDto(isbn, null, null, null, null));

		List<String> msgs = new ArrayList<>();
		msgs.add("SUCCESS");

		BookStoreResponse response = new BookStoreResponse(HttpStatus.OK.toString(), msgs, dtos);
		logger.info("BookController ::mediaCoverage():: exit");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping(value = "order/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookStoreResponse> orderBook(@PathVariable(value = "isbn", required = true) String isbn) {

		logger.info("BookController ::orderBook():: entry");
		BookDto dto = bookService.orderBook(new BookDto(isbn, null, null, null, null));

		List<String> msgs = new ArrayList<>();
		msgs.add("SUCCESS");

		BookStoreResponse response = new BookStoreResponse(HttpStatus.CREATED.toString(), msgs, dto);
		logger.info("BookController ::orderBook():: exit");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
