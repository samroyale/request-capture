package com.se.data;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple object that encapsulates a captured request. The request key contains the ID and an
 * optional tag.
 * 
 * @author seldred
 */
public class CapturedRequest {

	private static final long ID_NOT_SET = 0;
	
	private RequestKey key;
	
	private String method;
	
	private String url;
	
	private Map<String, String> headers;
	
	private String body;
	
	private Date timestamp;

	public CapturedRequest(String tag) {
		this(ID_NOT_SET, tag, new Date());
	}
	
	public CapturedRequest(long id, String tag, Date timestamp) {
		key = new RequestKey(id, tag);
		this.timestamp = timestamp;
	}

	public RequestKey getKey() {
		return key;
	}

	public void setKey(RequestKey key) {
		this.key = key;
	}

	public long getId() {
		return key.getId();
	}

	public void setId(long id) {
		key.setId(id);
	}

	public String getTag() {
		return key.getTag();
	}

	public String getFriendlyTag() {
		if (key.getTag() == null) {
			return "";
		}
		return key.getTag();
	}

	public void setTag(String tag) {
		key.setTag(tag);
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getHeadersAsString() {
		StringBuilder builder = new StringBuilder();
		for (String name: headers.keySet()) {
			builder.append(name + ": " + headers.get(name) + "\n");
		}
		return builder.toString();
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void setHeadersFromString(String headerFormat) {
		String[] nameValuePairs = headerFormat.split("\n");
		headers = new LinkedHashMap<String, String>();
		for (String nameValuePair: nameValuePairs) {
			String[] header = nameValuePair.split(": ");
			if (header.length > 1) {
				headers.put(header[0], header[1]);
			}
		}
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
