package com.se.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.se.data.CapturedRequest;
import com.se.data.CapturedRequestService;
import com.se.data.CapturedRequestServiceDatastoreImpl;

/**
 * Servlet that retrieves the most recent requests, either based on a given tag or not.
 * 
 * @author seldred
 */
@SuppressWarnings("serial")
public class RecentRequestsServlet extends HttpServlet {

	private CapturedRequestService capturedRequestService;
	
	public RecentRequestsServlet() {
		capturedRequestService = new CapturedRequestServiceDatastoreImpl();
	}
	
	public RecentRequestsServlet(CapturedRequestService capturedRequestService) {
		this.capturedRequestService = capturedRequestService;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tag = request.getParameter("tag");
		Breadcrumbs breadcrumbs = (Breadcrumbs) request.getAttribute("breadcrumbs");
		breadcrumbs.addBreadcrumb(getCurrentBreadcrumb(tag));
		List<CapturedRequest> capturedRequests = getCapturedRequests(tag);
		forwardRequest(request, response, capturedRequests);
	}

	private List<CapturedRequest> getCapturedRequests(String tag) {
		if ((tag == null) || (tag.isEmpty())) {
			return capturedRequestService.getMostRecent();			
		}
		return capturedRequestService.getMostRecentByTag(tag);
	}
	
	private Breadcrumb getCurrentBreadcrumb(String tag) {
		if ((tag == null) || (tag.isEmpty())) {
			return new Breadcrumb(Page.LIST);			
		}
		return new Breadcrumb(Page.LIST, tag);
	}
	
	private void forwardRequest(HttpServletRequest request, HttpServletResponse response,
			List<CapturedRequest> capturedRequests)
			throws ServletException, IOException {
		request.setAttribute("requests", capturedRequests);
		RequestDispatcher dispatcher = request.getRequestDispatcher("recent-requests.jsp");
		dispatcher.forward(request, response);
	}
}
