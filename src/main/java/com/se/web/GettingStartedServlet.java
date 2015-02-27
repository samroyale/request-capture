package com.se.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GettingStartedServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pageTitle = Page.START.getTitle();
		forwardRequest(request, response, pageTitle);
	}
	
	private void forwardRequest(HttpServletRequest request, HttpServletResponse response,
			String pageTitle)
			throws ServletException, IOException {
		request.setAttribute("pageTitle", pageTitle);
		RequestDispatcher dispatcher = request.getRequestDispatcher("getting-started.jsp");
		dispatcher.forward(request, response);
	}
}
