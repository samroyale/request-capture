package com.se.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.se.data.CapturedRequest;
import com.se.data.CapturedRequestService;

public class RecentRequestsServletTest {

	private List<CapturedRequest> requests;
	
	private Breadcrumbs breadcrumbs = Breadcrumbs.parseBreadcrumbs("h");
	
	@Before
	public void setup() {
		requests = new ArrayList<>();
		DateTime ts = new DateTime();
		requests.add(new CapturedRequest(1, "sam", ts.minusSeconds(10).toDate()));
		requests.add(new CapturedRequest(2, "sam", ts.minusSeconds(20).toDate()));
		requests.add(new CapturedRequest(3, "sam", ts.minusSeconds(30).toDate()));
	}
	
	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void recentRequestsDispatchedWithCorrectAttributes() throws Exception {
		CapturedRequestService service = mock(CapturedRequestService.class);
		when(service.getMostRecent()).thenReturn(requests);
		RecentRequestsServlet servlet = new RecentRequestsServlet(service);
		
		// mock request
		HttpServletRequest request = aMockedHttpRequestWithoutTag();
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		when(request.getRequestDispatcher("recent-requests.jsp")).thenReturn(dispatcher);
		ArgumentCaptor<List<CapturedRequest>> captured = ArgumentCaptor.forClass((Class) List.class);
		
		// mock response + captured args
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        
		servlet.doGet(request, response);
		
		// verify attributes were set correctly
		verify(request).setAttribute(Mockito.matches("requests"), captured.capture());
		assertThat(captured.getValue().size()).isEqualTo(3);
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void recentRequestsByTagDispatchedWithCorrectAttributes() throws Exception {
		CapturedRequestService service = mock(CapturedRequestService.class);
		when(service.getMostRecentByTag("sam")).thenReturn(requests);
		RecentRequestsServlet servlet = new RecentRequestsServlet(service);
		
		// mock request
		HttpServletRequest request = aMockedHttpRequestWithTag();
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		when(request.getRequestDispatcher("recent-requests.jsp")).thenReturn(dispatcher);
		ArgumentCaptor<List<CapturedRequest>> captured = ArgumentCaptor.forClass((Class) List.class);
		
		// mock response + captured args
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        
		servlet.doGet(request, response);
		
		// verify attributes were set correctly
		verify(request).setAttribute(Mockito.matches("requests"), captured.capture());
		assertThat(captured.getValue().size()).isEqualTo(3);
	}

	private HttpServletRequest aMockedHttpRequestWithoutTag() throws Exception {
		HttpServletRequest request =  MockRequestBuilder.anInstance()
				.withMethod("POST")
				.withUri("/detail")
				.build();
		when(request.getAttribute("breadcrumbs")).thenReturn(breadcrumbs);
		when(request.getParameter("tag")).thenReturn(null);
		return request;
	}

	private HttpServletRequest aMockedHttpRequestWithTag() throws Exception {
		HttpServletRequest request =  MockRequestBuilder.anInstance()
				.withMethod("POST")
				.withUri("/detail")
				.withQueryString("tag=sam")
				.build();
		when(request.getAttribute("breadcrumbs")).thenReturn(breadcrumbs);
		when(request.getParameter("tag")).thenReturn("sam");
		return request;
	}
}
