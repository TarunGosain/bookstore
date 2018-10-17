package com.netent.bookstore.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.netent.bookstore.entities.Book;

public class BookDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String isbn;

	private String title;

	private String author;

	private Double price;

	@JsonIgnore
	private Integer count;

	public BookDto(@JsonProperty("isbn") String isbn, @JsonProperty("title") String title,
			@JsonProperty("author") String author, @JsonProperty("price") Double price,
			@JsonProperty("count") Integer count) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.price = price;
		this.count = count;
	}

	public BookDto(Book book) {
		this.isbn = book.getIsbn();
		this.title = book.getTitle();
		this.author = book.getAuthor();
		this.price = book.getPrice();
		this.count = book.getCount();
	}

	public Book toEntity() {
		return new Book(isbn, title, author, price, count);
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "BookDto [isbn=" + isbn + ", title=" + title + ", author=" + author + ", price=" + price + ", count="
				+ count + "]";
	}

}
