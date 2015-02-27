package com.se.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ListRequestsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tag = request.getParameter("tag");
		String pageTitle = getPageTitle(tag);
		String recentRequestsUrl = getRecentRequestsUrl(tag);
		forwardRequest(request, response, pageTitle, recentRequestsUrl);
	}

	private String getPageTitle(String tag) {
		if ((tag == null) || (tag.isEmpty())) {
			return Page.LIST.getTitle();	
		}
		return String.format("%s [%s]", Page.LIST.getTitle(), tag);		
	}

	private String getRecentRequestsUrl(String tag) {
		if ((tag == null) || (tag.isEmpty())) {
			return "requests?bc=";
		}
		return String.format("requests?tag=%s&bc=", tag);
	}

	private void forwardRequest(HttpServletRequest request, HttpServletResponse response,
			String pageTitle, String recentRequestsUrl)
			throws ServletException, IOException {
		request.setAttribute("pageTitle", pageTitle);
		request.setAttribute("recentRequestsUrl", recentRequestsUrl);
		RequestDispatcher dispatcher = request.getRequestDispatcher("list-requests.jsp");
		dispatcher.forward(request, response);
	}
}
