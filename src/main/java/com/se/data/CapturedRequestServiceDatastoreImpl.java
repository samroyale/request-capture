package com.se.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;

public class CapturedRequestServiceDatastoreImpl implements CapturedRequestService {

	private static final int MOST_RECENT_COUNT = 10;
	
	private static final int POPULAR_TAG_COUNT = 100;
	
	private static final String TAG_NAME = "Tag";

	private static final String CAPTURED_REQUEST_NAME = "CapturedRequest";
	
	private static final String METHOD_PROPERTY = "method";

	private static final String URL_PROPERTY = "url";
	
	private static final String HEADERS_PROPERTY = "headers";

	private static final String BODY_PROPERTY = "body";

	private static final String TIMESTAMP_PROPERTY = "timestamp";

	@Override
	public CapturedRequest persist(CapturedRequest request) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = datastore.put(toEntity(request));
        request.setId(key.getId());
        return request;
	}
	
	@Override
	public CapturedRequest getByKey(RequestKey requestKey) {
	    return getCapturedRequest(getKey(requestKey));
	}

	@Override
	public List<CapturedRequest> getMostRecent() {
		Query query = new Query(CAPTURED_REQUEST_NAME).addSort(TIMESTAMP_PROPERTY, Query.SortDirection.DESCENDING);
	    return getCapturedRequests(query, MOST_RECENT_COUNT);
	}

	@Override
	public List<CapturedRequest> getMostRecentByTag(String tag) {
	    Key tagKey = KeyFactory.createKey(TAG_NAME, tag);
	    Query query = new Query(CAPTURED_REQUEST_NAME, tagKey).addSort(TIMESTAMP_PROPERTY, Query.SortDirection.DESCENDING);
	    return getCapturedRequests(query, MOST_RECENT_COUNT);
	}
	
	@Override
	public List<TagCount> getPopularTags() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(CAPTURED_REQUEST_NAME).addSort(TIMESTAMP_PROPERTY, Query.SortDirection.DESCENDING);
	    List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(POPULAR_TAG_COUNT));
	    return getTagCounts(entities);
	}

	private Key getKey(RequestKey requestKey) {
		if (requestKey.getTag() == null) {
			return KeyFactory.createKey(CAPTURED_REQUEST_NAME, requestKey.getId());
		}
        Key tagKey = KeyFactory.createKey(TAG_NAME, requestKey.getTag());
	    return KeyFactory.createKey(tagKey, CAPTURED_REQUEST_NAME, requestKey.getId());
	}
	
	private Entity toEntity(CapturedRequest request) {
        Entity entity = createEntity(request);
        entity.setProperty(METHOD_PROPERTY, request.getMethod());
        entity.setProperty(URL_PROPERTY, request.getUrl());
        entity.setProperty(HEADERS_PROPERTY, new Text(request.getHeadersAsString()));
        entity.setProperty(BODY_PROPERTY, new Text(request.getBody()));
        entity.setProperty(TIMESTAMP_PROPERTY, request.getTimestamp());
        return entity;
	}
	
	private CapturedRequest getCapturedRequest(Key key) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    try {
	    	return toCapturedRequest(datastore.get(key));
	    }
	    catch (EntityNotFoundException e) {
	    	// TODO: log this
	    }
	    return null;
	}
	
	private List<CapturedRequest> getCapturedRequests(Query query, int count) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(count));
	    return toCapturedRequestList(entities);
	}
	
	private List<CapturedRequest> toCapturedRequestList(List<Entity> entities) {
	    List<CapturedRequest> requests = new ArrayList<CapturedRequest>();
	    for (Entity entity: entities) {
	    	requests.add(toCapturedRequest(entity));
	    }
	    return requests;
	}
	
	private Entity createEntity(CapturedRequest request) {
		if (request.getTag() == null) {
			return new Entity(CAPTURED_REQUEST_NAME);
		}
        Key tagKey = KeyFactory.createKey(TAG_NAME, request.getTag());
        return new Entity(CAPTURED_REQUEST_NAME, tagKey);
	}
	
	private CapturedRequest toCapturedRequest(Entity entity) {
		long id = entity.getKey().getId();
		String tag = getTag(entity);
		Date timestamp = (Date) entity.getProperty(TIMESTAMP_PROPERTY);
		CapturedRequest request = new CapturedRequest(id, tag, timestamp);
		request.setMethod((String) entity.getProperty(METHOD_PROPERTY));
		request.setUrl((String) entity.getProperty(URL_PROPERTY));
		request.setHeadersFromString(((Text) entity.getProperty(HEADERS_PROPERTY)).getValue());
		request.setBody(((Text) entity.getProperty(BODY_PROPERTY)).getValue());
		return request;
	}
	
	private String getTag(Entity entity) {
		Key parent = entity.getParent();
		if (parent == null) {
			return null;
		}
		return parent.getName();
	}
	
	private List<TagCount> getTagCounts(List<Entity> entities) {
		Map<String, Integer> tagCountMap = getTagCountMap(entities);
	    List<TagCount> tagCounts = new ArrayList<TagCount>();
	    for (String tag: tagCountMap.keySet()) {
	    	tagCounts.add(new TagCount(tag, tagCountMap.get(tag)));
	    }
	    Collections.sort(tagCounts, new TagCountComparator());
	    if (tagCounts.size() > 10) {
	    	return tagCounts.subList(0, 10);
	    }
	    return tagCounts;
	}

	private Map<String, Integer> getTagCountMap(List<Entity> entities) {
		Map<String, Integer> tagCountMap = new HashMap<String, Integer>();
	    for (Entity entity: entities) {
	    	String tag = getTag(entity);
	    	if (tag != null) {
		    	if (tagCountMap.containsKey(tag)) {
		    		int tagCount = tagCountMap.get(tag);
		    		tagCountMap.put(tag, ++tagCount);
		    	}
		    	else {
		    		tagCountMap.put(tag, 1);	    		
		    	}
	    	}
	    }
		return tagCountMap;
	}
	
	private static class TagCountComparator implements Comparator<TagCount> {
		@Override
		public int compare(TagCount tc1, TagCount tc2) {
			int count1 = tc1.getCount();
			int count2 = tc2.getCount();
			if (count2 < count1) {
				return -1;
			} else if (count1 < count2) {
				return 1;
			}
			return 0;
		}
	}
}
