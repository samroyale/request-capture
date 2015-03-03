package com.se.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.se.data.CapturedRequest;

/**
 * Builds a CapturedRequest instance from the given HTTP request.
 * 
 * @author seldred
 */
public class CapturedRequestBuilder {

	private static final String CAPTURE_PREFIX = "/capture";
	
	private static final String FORWARD_SLASH = "/";

	private HttpServletRequest request;
	
	public CapturedRequestBuilder(HttpServletRequest request) {
		this.request = request;
	}
	
	public CapturedRequest build() throws IOException {
		String uri = request.getRequestURI();
		String tag = getTag(uri);
		CapturedRequest capturedRequest = new CapturedRequest(tag);
		capturedRequest.setMethod(request.getMethod());
		capturedRequest.setUrl(getUrl(uri));
		capturedRequest.setHeaders(getHeaders());
		capturedRequest.setBody(getBody());
		return capturedRequest;
	}

	private String getTag(String uri) {
		int startIndex = uri.indexOf(CAPTURE_PREFIX);
		if (startIndex < 0) {
			return null;
		}
		// extract the next path token
		int fromIndex = startIndex + CAPTURE_PREFIX.length() + 1;
		if (uri.length() <= fromIndex) {
			return null;
		}
		int toIndex = uri.indexOf(FORWARD_SLASH, fromIndex);
		if (toIndex < 0) {
			return uri.substring(fromIndex);
		}
		return uri.substring(fromIndex, toIndex);		
	}
	
	private String getUrl(String uri) {
		String queryString = request.getQueryString();
		if (queryString == null) {
			return uri;
		}
		return uri + "?" + queryString;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, String> getHeaders() {
		Map<String, String> headerMap = new LinkedHashMap<String, String>();
		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String name = (String) headers.nextElement();
			headerMap.put(name, request.getHeader(name));
		}
		return headerMap;
	}

	private String getBody() throws IOException {
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		BufferedReader reader = request.getReader();
		String line = null;
		while ((line = reader.readLine()) != null) {
			out.println(line);
		}
		return writer.toString();
	}
}
