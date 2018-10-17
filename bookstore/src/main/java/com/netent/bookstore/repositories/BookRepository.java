package com.netent.bookstore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.netent.bookstore.entities.Book;

public interface BookRepository extends JpaRepository<Book, String> {

	String FIND_BOOK = "SELECT b FROM Book b WHERE " + "LOWER(b.isbn) LIKE LOWER(:isbn) OR "
			+ "LOWER(b.title) LIKE LOWER(CONCAT('%',:title, '%')) OR "
			+ "LOWER(b.author) LIKE LOWER(CONCAT('%',:author, '%'))";

	@Query(value = FIND_BOOK)
	List<Book> findBook(@Param("isbn") String isbn, @Param("title") String title, @Param("author") String author);

}
