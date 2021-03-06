package com.se.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that echoes the HTTP request back to the sender in plain text.
 * 
 * @author seldred
 */
@SuppressWarnings("serial")
public class EchoRequestServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		RequestPrinter printer = new RequestPrinter(request, response.getWriter());
		printer.printRequest();
	}
}
