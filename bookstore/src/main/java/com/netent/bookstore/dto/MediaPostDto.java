package com.netent.bookstore.dto;

import java.io.Serializable;

public class MediaPostDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MediaPostDto(String title) {
		super();
		this.title = title;
	}

	@Override
	public String toString() {
		return "MediaPostDto [title=" + title + "]";
	}

}
