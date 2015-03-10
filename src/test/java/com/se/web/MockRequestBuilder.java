package com.se.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

public class MockRequestBuilder {
	
	private String method;
	
	private String uri;
	
	private String queryString;
	
	private String body = "";
	
	private Map<String, String> headers = new HashMap<>();
	
	private MockRequestBuilder() {
		// use anInstance()
	}
	
	static MockRequestBuilder anInstance() {
		return new MockRequestBuilder();
	}
	
	MockRequestBuilder withMethod(String method) {
		this.method = method;
		return this;
	}
	
	MockRequestBuilder withUri(String uri) {
		this.uri = uri;
		return this;
	}
	
	MockRequestBuilder withQueryString(String queryString) {
		this.queryString = queryString;
		return this;
	}
	
	MockRequestBuilder withBody(String body) {
		this.body = body;
		return this;
	}

	MockRequestBuilder withHeaders(Map<String, String> headers) {
		this.headers = headers;
		return this;
	}
	
	HttpServletRequest build() throws IOException {
		HttpServletRequest httpRequest = mock(HttpServletRequest.class);
		when(httpRequest.getMethod()).thenReturn(method);
		when(httpRequest.getRequestURI()).thenReturn(uri);
		when(httpRequest.getQueryString()).thenReturn(queryString);
		setupHeaders(httpRequest);
		StringReader in = new StringReader(body);
		when(httpRequest.getReader()).thenReturn(new BufferedReader(in));
		return httpRequest;
	}
	
	private void setupHeaders(HttpServletRequest httpRequest) {
		// going back a bit here!
		Vector<String> v = new Vector<>();
		for (Map.Entry<String, String> entry: headers.entrySet()) {
			v.add(entry.getKey());
			when(httpRequest.getHeader(entry.getKey())).thenReturn(entry.getValue());
		}
		when(httpRequest.getHeaderNames()).thenReturn(v.elements());
	}
}
