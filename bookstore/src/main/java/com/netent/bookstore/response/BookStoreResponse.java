package com.netent.bookstore.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookStoreResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("code")
	private String code;

	@JsonProperty("messages")
	private List<String> messages;

	@JsonProperty("payload")
	private Object payload;

	public BookStoreResponse(String code, List<String> messages, Object payload) {
		this.code = code;
		this.messages = messages;
		this.payload = payload;
	}

	public BookStoreResponse() {
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public String getCode() {
		return code;
	}

	public List<String> getMessages() {
		return messages;
	}

	public Object getPayload() {
		return payload;
	}

}
