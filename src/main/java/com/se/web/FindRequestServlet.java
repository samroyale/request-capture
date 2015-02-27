package com.se.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.se.data.RequestKey;

@SuppressWarnings("serial")
public class FindRequestServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String keyFormat = request.getParameter("key");
		RequestKey key = RequestKey.parseKey(keyFormat);
		response.sendRedirect(getRedirectUrl(key));
	}

	private String getRedirectUrl(RequestKey key) {
		if (key.getTag() == null) {
			return String.format("detail?id=%s", key.getId());
		}
		return String.format("detail?id=%s&tag=%s", key.getId(), key.getTag());
	}
}
