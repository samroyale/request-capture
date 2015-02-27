package com.se.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class RequestPrinter {

	private HttpServletRequest request;
	
	private PrintWriter out;
	
	public RequestPrinter(HttpServletRequest request, PrintWriter out) {
		this.request = request;
		this.out = out;
	}
	
	public String printRequest() throws IOException {
		String title = getTitle();
		out.println(title);
		out.println("\n<< HEADERS >>");
		printHeaders();
		out.println("\n<< BODY >>");
		printBody();
		return title;
	}
	
	private String getTitle() {
		return request.getMethod() + " " + getLocation(); 
	}
	
	private String getLocation() {
		String uri = request.getRequestURI();
		String queryString = request.getQueryString();
		if (queryString == null) {
			return uri;
		}
		return uri + "?" + queryString;
	}

	private void printHeaders() {
		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String name = headers.nextElement();
			out.println(name + ": " + request.getHeader(name));
		}
	}

	private void printBody() throws IOException {
		BufferedReader reader = request.getReader();
		String line = null;
		while ((line = reader.readLine()) != null) {
			out.println(line);
		}
	}
}
