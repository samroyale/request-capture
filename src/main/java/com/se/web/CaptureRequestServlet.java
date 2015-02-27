package com.se.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.se.data.CapturedRequest;
import com.se.data.CapturedRequestService;
import com.se.data.CapturedRequestServiceDatastoreImpl;

@SuppressWarnings("serial")
public class CaptureRequestServlet extends HttpServlet {

	private CapturedRequestService capturedRequestService = new CapturedRequestServiceDatastoreImpl();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CapturedRequestBuilder builder = new CapturedRequestBuilder(request);
		CapturedRequest capturedRequest = builder.build();
		capturedRequestService.persist(capturedRequest);
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(getOutput(capturedRequest));
	}
	
	private String getOutput(CapturedRequest capturedRequest) {
		return capturedRequest.getMethod() + " " + capturedRequest.getUrl() + " " + capturedRequest.getKey();
	}
}
