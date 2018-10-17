package com.netent.bookstore.controller;

import static org.mockito.Mockito.when;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.netent.bookstore.dto.BookDto;
import com.netent.bookstore.exception.BookStoreExceptionHandler;
import com.netent.bookstore.services.BookService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {
	private MockMvc mockMvc;

	@Mock
	private BookService bookService;

	@InjectMocks
	private BookController bookController;

	private BookDto bookDto = null;
	private List<BookDto> bookDtos = null;

	private static String ISBN = "TEST-ISBN";
	private static String TITLE = "TEST-TITLE";
	private static String AUTHOR = "TEST-AUTHOR";
	private static Double PRICE = 100d;
	private static Integer COUNT = 1;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(bookController)
				.setControllerAdvice(new BookStoreExceptionHandler(new ReloadableResourceBundleMessageSource()))
				.build();
		bookDto = new BookDto(ISBN, TITLE, AUTHOR, PRICE, COUNT);
		bookDtos = new ArrayList<>();
		bookDtos.add(bookDto);
	}

	@Test
	public void shouldCreateBookSuccessfully() throws Exception {
		when(bookService.createBook(Mockito.any())).thenReturn(bookDto);
		ClassLoader classLoader = getClass().getClassLoader();
		String json = IOUtils.toString(classLoader.getResourceAsStream("book.json"));

		mockMvc.perform(post("/api/v1/bookstore/add").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(json)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.payload.isbn").value("TEST-ISBN"))
				.andExpect(jsonPath("$.payload.title").value("TEST-TITLE"))
				.andExpect(jsonPath("$.payload.author").value("TEST-AUTHOR"));

	}

	@Ignore
	@Test
	public void shouldGetBookByIsbnSuccessfully() throws Exception {
		when(bookService.searchBook(Mockito.any())).thenReturn(bookDtos);

		mockMvc.perform(get("/api/v1/bookstore/search?isbn=TEST-ISBN&title=TEST-TITLE")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(jsonPath("$.payload.isbn").value("TEST-ISBN"))
				.andExpect(jsonPath("$.payload.title").value("TEST-TITLE"))
				.andExpect(jsonPath("$.payload.author").value("TEST-AUTHOR"));

	}

}
