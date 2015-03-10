package com.se.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.se.data.CapturedRequest;
import com.se.data.CapturedRequestService;
import com.se.data.RequestKey;

public class RequestDetailServletTest {

	@Test
	public void requestDetailNotFoundHandledOkay() throws Exception {
		CapturedRequestService service = mock(CapturedRequestService.class);
		when(service.getByKey(expectedRequestKey())).thenReturn(null);
		RequestDetailServlet servlet = new RequestDetailServlet(service);
		
		// mock request
		HttpServletRequest request = aMockedRequestDetailRequest();
		
		// mock response + captured args
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        ArgumentCaptor<Integer> capturedInt = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> capturedString = ArgumentCaptor.forClass(String.class);
        
		servlet.doGet(request, response);
		
		// verify response was called with correct args
		verify(response).sendError(capturedInt.capture(), capturedString.capture());
		assertThat(capturedInt.getValue()).isEqualTo(404);
		assertThat(capturedString.getValue()).isEqualTo("Key not found: 1/sam");
	}
	
	@Test
	public void requestDetailDispatchedWithCorrectAttributes() throws Exception {
		CapturedRequestService service = mock(CapturedRequestService.class);
		when(service.getByKey(expectedRequestKey())).thenReturn(new CapturedRequest(1, "sam", new Date()));
		RequestDetailServlet servlet = new RequestDetailServlet(service);
		
		// mock request
		HttpServletRequest request = aMockedRequestDetailRequest();
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		when(request.getRequestDispatcher("request-detail.jsp")).thenReturn(dispatcher);
        ArgumentCaptor<String> capturedString = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<CapturedRequest> capturedRequest = ArgumentCaptor.forClass(CapturedRequest.class);
		
		// mock response + captured args
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        
		servlet.doGet(request, response);
		
		// verify attributes were set correctly
		verify(request).setAttribute(Mockito.matches("pageTitle"), capturedString.capture());
		assertThat(capturedString.getValue()).isEqualTo("Request Detail [1/sam]");
		verify(request).setAttribute(Mockito.matches("capturedRequest"), capturedRequest.capture());
		assertThat(capturedRequest.getValue().getId()).isEqualTo(1);
		assertThat(capturedRequest.getValue().getTag()).isEqualTo("sam");
	}

	private HttpServletRequest aMockedRequestDetailRequest() throws Exception {
		HttpServletRequest request =  MockRequestBuilder.anInstance()
				.withMethod("POST")
				.withUri("/detail")
				.withQueryString("id=1&tag=sam")
				.build();
		when(request.getParameter("id")).thenReturn("1");
		when(request.getParameter("tag")).thenReturn("sam");
		return request;
	}
	
	private RequestKey expectedRequestKey() {
		return argThat(new RequestKeyMatcher(1, "sam"));
	}
}
