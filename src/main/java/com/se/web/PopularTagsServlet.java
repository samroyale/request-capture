package com.se.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.se.data.CapturedRequestService;
import com.se.data.CapturedRequestServiceDatastoreImpl;
import com.se.data.TagCount;

/**
 * Servlet that retrieves the most popular tags from the last n requests.
 * 
 * @author seldred
 */
@SuppressWarnings("serial")
public class PopularTagsServlet extends HttpServlet {

	private CapturedRequestService capturedRequestService = new CapturedRequestServiceDatastoreImpl();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<TagCount> tagCounts = capturedRequestService.getPopularTags();
		forwardRequest(request, response, tagCounts);
	}

	private void forwardRequest(HttpServletRequest request,
			HttpServletResponse response, List<TagCount> tagCounts)
			throws ServletException, IOException {
		request.setAttribute("tagCounts", tagCounts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("popular-tags.jsp");
		dispatcher.forward(request, response);
	}
}
