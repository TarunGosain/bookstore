package com.netent.bookstore.services;

import java.util.List;

import com.netent.bookstore.dto.BookDto;
import com.netent.bookstore.dto.MediaPostDto;

public interface BookService {

	BookDto createBook(BookDto dto);

	List<BookDto> searchBook(BookDto dto);

	List<MediaPostDto> searchMediaCoverageByIsbn(BookDto dto);

	BookDto orderBook(BookDto dto);
}
