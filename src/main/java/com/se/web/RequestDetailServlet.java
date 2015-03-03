package com.se.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.se.data.CapturedRequest;
import com.se.data.CapturedRequestService;
import com.se.data.CapturedRequestServiceDatastoreImpl;
import com.se.data.RequestKey;

/**
 * Retrieves a single request based on the given key.
 * 
 * @author seldred
 */
@SuppressWarnings("serial")
public class RequestDetailServlet extends HttpServlet {

	private CapturedRequestService capturedRequestService = new CapturedRequestServiceDatastoreImpl();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long requestId = Long.parseLong(request.getParameter("id"));
		String tag = request.getParameter("tag");
		RequestKey key = getRequestKey(requestId, tag);
		CapturedRequest capturedRequest = capturedRequestService.getByKey(key);
		if (capturedRequest == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Key not found: " + key);
			return;
		}
		
		String pageTitle = getPageTitle(capturedRequest.getKey());

		forwardRequest(request, response, pageTitle, capturedRequest);
	}

	private String getPageTitle(RequestKey key) {
		return String.format("%s [%s]", Page.DETAIL.getTitle(), key);
	}
	
	private RequestKey getRequestKey(long requestId, String tag) {
		if ((tag == null) || (tag.isEmpty())) {
			return new RequestKey(requestId);
		}
		return new RequestKey(requestId, tag);
	}
	
	private void forwardRequest(HttpServletRequest request, HttpServletResponse response,
			String pageTitle, CapturedRequest capturedRequest)
			throws ServletException, IOException {
		request.setAttribute("pageTitle", pageTitle);
		request.setAttribute("capturedRequest", capturedRequest);
		RequestDispatcher dispatcher = request.getRequestDispatcher("request-detail.jsp");
		dispatcher.forward(request, response);
	}
}
