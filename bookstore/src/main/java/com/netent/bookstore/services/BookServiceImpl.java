package com.netent.bookstore.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netent.bookstore.dto.BookDto;
import com.netent.bookstore.dto.MediaPostDto;
import com.netent.bookstore.entities.Book;
import com.netent.bookstore.exception.BookStoreException;
import com.netent.bookstore.exception.ResourceNotFoundException;
import com.netent.bookstore.model.mediaSearch.MediaPost;
import com.netent.bookstore.repositories.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	public static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	@Autowired
	private BookRepository bookRepository;

	@Value("${media-coverage-api}")
	String mediaCoverageApi;

	@Override
	public BookDto createBook(BookDto dto) {
		Book book = dto.toEntity();
		book = bookRepository.save(book);
		return ((null == book) ? null : new BookDto(book));
	}

	@Override
	public List<BookDto> searchBook(BookDto dto) {
		String isbn = null == dto.getIsbn() ? "null!!!" : dto.getIsbn();
		String title = null == dto.getTitle() ? "null!!!" : dto.getTitle();
		String author = null == dto.getAuthor() ? "null!!!" : dto.getAuthor();
		List<Book> books = bookRepository.findBook(isbn, title, author);

		List<BookDto> bookDtos = books.stream().map(book -> new BookDto(book)).collect(Collectors.toList());

		return bookDtos;
	}

	@Override
	public List<MediaPostDto> searchMediaCoverageByIsbn(BookDto dto) {

		String bookTitle = getBookTitle(dto);
		if (null == bookTitle) {
			return new ArrayList<>();
		}

		ResponseEntity<List<MediaPost>> response = new RestTemplate().exchange(mediaCoverageApi, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<MediaPost>>() {
				});

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new BookStoreException("Error fetching posts from media search api");
		}

		List<MediaPost> mediaPosts = response.getBody();
		mediaPosts.forEach(post -> {
			post.setTitle(post.getTitle().replaceAll("\n", " "));
			post.setBody(post.getBody().replaceAll("\n", " "));
		});

		Predicate<MediaPost> p1 = post -> post.getTitle().equalsIgnoreCase(bookTitle);
		Predicate<MediaPost> p2 = post -> post.getBody().equalsIgnoreCase(bookTitle);

		List<MediaPostDto> matchingPosts = mediaPosts.stream().filter(p1.or(p2))
				.map(post -> new MediaPostDto(post.getTitle())).collect(Collectors.toList());

		return matchingPosts;

	}

	@Override
	public BookDto orderBook(BookDto dto) {
		Book book = null;
		try {
			Optional<Book> optionalBook = bookRepository.findById(dto.getIsbn());
			if (optionalBook.isPresent()) {
				book = optionalBook.get();
				book.setCount(book.getCount() - 1);
				bookRepository.save(book);
			} else {
				throw new ResourceNotFoundException("Book with ISBN " + dto.getIsbn() + " does not exists");
			}
		} catch (OptimisticLockException ex) {
			throw new BookStoreException("Error processing order. Please try again.");
		}
		return ((null == book) ? null : new BookDto(book));
	}

	private String getBookTitle(BookDto dto) {
		Optional<BookDto> bookDto = searchBook(dto).stream().findFirst();
		return bookDto.isPresent() ? bookDto.get().getTitle() : null;
	}

};