package com.se.data;

/**
 * A key for captured requests that contains the ID and an optional tag.
 *  
 * @author seldred
 */
public class RequestKey {

	private long id;
	
	private String tag;

	public RequestKey(long id) {
		this.id = id;
	}
	
	public RequestKey(long id, String tag) {
		this(id);
		this.tag = tag;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String toString() {
		if ((tag == null) || (tag.isEmpty())) {
			return Long.toString(id);
		}
		return String.format("%s/%s", id, tag);
	}
	
	public static RequestKey parseKey(String keyFormat) {
		String[] idAndTag = keyFormat.split("/");
		if ((idAndTag.length == 0) || (idAndTag.length > 2)) {
			throw new InvalidKeyFormatException("Incorrect key format: " + keyFormat);
		}
		try {
			long requestId = Long.parseLong(idAndTag[0]);
			if (idAndTag.length == 1) {
				return new RequestKey(requestId);
			}
			return new RequestKey(requestId, idAndTag[1]);
		}
		catch (NumberFormatException e) {
			throw new InvalidKeyFormatException("Incorrect key format: " + keyFormat, e);			
		}
	}
}
