package com.se.data;

import java.util.List;

/**
 * Interface for the captured request service. Describes all the operations needed by the app to
 * interact with the underlying datastore.
 * 
 * @author seldred
 */
public interface CapturedRequestService {

	public CapturedRequest persist(CapturedRequest request);

	public CapturedRequest getByKey(RequestKey key);
		
	public List<CapturedRequest> getMostRecent();
	
	public List<CapturedRequest> getMostRecentByTag(String tag);

	public List<TagCount> getPopularTags();
}