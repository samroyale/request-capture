package com.se.web;

import org.mockito.ArgumentMatcher;

import com.se.data.RequestKey;

public class RequestKeyMatcher extends ArgumentMatcher<RequestKey> {

	private long id;
	
	private String tag;
	
	RequestKeyMatcher(long id, String tag) {
		this.id = id;
		this.tag = tag;
	}
	
	public boolean matches(Object obj) {
		RequestKey key = (RequestKey) obj;
		return key.getId() == id && key.getTag().equals(tag);
	}	
}
