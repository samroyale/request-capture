package com.se.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import com.se.data.CapturedRequest;

public class CapturedRequestBuilderTest {

	@Test
	public void buildsCapturedRequestWithoutQueryString() throws Exception {
		HttpServletRequest httpRequest = aMockedHttpRequestWithoutQueryString();
		CapturedRequestBuilder builder = new CapturedRequestBuilder(httpRequest);
		CapturedRequest request = builder.build();
		assertThat(request.getMethod()).isEqualTo("POST");
		assertThat(request.getUrl()).isEqualTo("/capture/this/request");
		assertThat(request.getTag()).isEqualTo("this");
		assertThat(request.getBody()).isEqualTo("unit testing is great\n"); // always ends with new line
		assertThat(request.getHeadersAsString()).isEqualTo("header1: value1\nheader2: value2\n");
		assertThat(request.getHeaders()).contains(entry("header1", "value1"),
				entry("header2", "value2"));
	}
	
	@Test
	public void buildsCapturedRequestWithQueryString() throws Exception {
		HttpServletRequest httpRequest = aMockedHttpRequestWithQueryString();
		CapturedRequestBuilder builder = new CapturedRequestBuilder(httpRequest);
		CapturedRequest request = builder.build();
		assertThat(request.getUrl()).isEqualTo("/capture/this/request?some=value");
	}

	@Test
	public void handlesRequestWithoutTag() throws Exception {
		HttpServletRequest httpRequest = aMockedHttpRequestWithoutTag();
		CapturedRequestBuilder builder = new CapturedRequestBuilder(httpRequest);
		CapturedRequest request = builder.build();
		assertThat(request.getMethod()).isEqualTo("GET");
		assertThat(request.getUrl()).isEqualTo("/capture?some=value");
		assertThat(request.getTag()).isNull();
		assertThat(request.getFriendlyTag()).isEmpty();
	}

	private HttpServletRequest aMockedHttpRequestWithoutQueryString() throws Exception {
		return aMockedHttpRequest("POST", "/capture/this/request", null);
	}
	
	private HttpServletRequest aMockedHttpRequestWithQueryString() throws Exception {
		return aMockedHttpRequest("POST", "/capture/this/request", "some=value");
	}
	
	private HttpServletRequest aMockedHttpRequestWithoutTag() throws Exception {
		return aMockedHttpRequest("GET", "/capture", "some=value");
	}
	
	private HttpServletRequest aMockedHttpRequest(String method, String uri, String queryString) throws Exception {
		HttpServletRequest httpRequest = mock(HttpServletRequest.class);
		when(httpRequest.getMethod()).thenReturn(method);
		when(httpRequest.getRequestURI()).thenReturn(uri);
		when(httpRequest.getQueryString()).thenReturn(queryString);
		when(httpRequest.getHeaderNames()).thenReturn(getHeadersEnumeration());
		when(httpRequest.getHeader("header1")).thenReturn("value1");
		when(httpRequest.getHeader("header2")).thenReturn("value2");
		StringReader in = new StringReader("unit testing is great");
		when(httpRequest.getReader()).thenReturn(new BufferedReader(in));
		return httpRequest;
	}
	
	public Enumeration<String> getHeadersEnumeration() {
		// going back a bit here!
		Vector<String> v = new Vector<>();
		v.add("header1");
		v.add("header2");
		return v.elements();
	}
}
