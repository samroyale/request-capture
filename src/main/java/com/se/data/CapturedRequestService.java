package com.se.data;

import java.util.List;

public interface CapturedRequestService {

	public CapturedRequest persist(CapturedRequest request);

	public CapturedRequest getByKey(RequestKey key);
		
	public List<CapturedRequest> getMostRecent();
	
	public List<CapturedRequest> getMostRecentByTag(String tag);

	public List<TagCount> getPopularTags();
}