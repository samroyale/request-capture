package com.se.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.verify;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.se.data.CapturedRequest;

public class CapturedRequestBuilderTest {

	private Map<String, String> headers;
	
	@Before
	public void setup() {
		headers = new LinkedHashMap<>();
		headers.put("header1", "value1");
		headers.put("header2", "value2");
	}
	
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
		return MockRequestBuilder.anInstance()
				.withMethod("POST")
				.withUri("/capture/this/request")
				.withHeaders(headers)
				.withBody("unit testing is great\n")
				.build();
	}
	
	private HttpServletRequest aMockedHttpRequestWithQueryString() throws Exception {
		return MockRequestBuilder.anInstance()
				.withMethod("POST")
				.withUri("/capture/this/request")
				.withQueryString("some=value")
				.withHeaders(headers)
				.withBody("unit testing is great\n")
				.build();
	}
	
	private HttpServletRequest aMockedHttpRequestWithoutTag() throws Exception {
		return MockRequestBuilder.anInstance()
				.withMethod("GET")
				.withUri("/capture")
				.withQueryString("some=value")
				.withHeaders(headers)
				.withEmptyBody()
				.build();
	}
}
