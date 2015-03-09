package com.se.data;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.se.data.CapturedRequest;
import com.se.data.CapturedRequestService;
import com.se.data.CapturedRequestServiceDatastoreImpl;

/**
 * CapturedRequestService:
 * * public CapturedRequest persist(CapturedRequest request);
 * * public CapturedRequest getByKey(RequestKey key);
 * * public List<CapturedRequest> getMostRecent();
 * * public List<CapturedRequest> getMostRecentByTag(String tag);
 * * public List<TagCount> getPopularTags();
 * 
 * @author seldred
 */
public class CapturedRequestServiceDatastoreImplTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    
    private final CapturedRequestService service = new CapturedRequestServiceDatastoreImpl();
    
    private DatastoreService ds; 

    @Before
    public void setUp() {
        helper.setUp();
        ds = DatastoreServiceFactory.getDatastoreService();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void capturedRequestsArePersisted() {
        doPersistTest();
    }

    @Test
    public void capturedRequestsArePersistedAgain() {
        doPersistTest();
    }
    
    @Test
    public void capturedRequestsCanByRetrievedByKey() {
        CapturedRequest cr1 = service.persist(aCapturedRequestWithTag("sam"));
        CapturedRequest cr11 = service.getByKey(cr1.getKey());
        assertThat(cr1.getKey().toString()).isEqualTo(cr11.getKey().toString());
    }
    
    @Test
    public void mostRecentRequestsAreInTheRightOrder() {
    	assertThat(service.getMostRecent()).hasSize(0);
    	setupData();
    	assertThat(service.getMostRecent()).extracting("tag", "body")
				.containsExactly(tuple("sam0", "inserted: 0, 0"), 
						tuple("sam1", "inserted: 1, 0"),
						tuple("sam1", "inserted: 1, 1"),
						tuple("sam2", "inserted: 2, 0"),
						tuple("sam2", "inserted: 2, 1"),
						tuple("sam2", "inserted: 2, 2"),
						tuple("sam3", "inserted: 3, 0"),
						tuple("sam3", "inserted: 3, 1"),
						tuple("sam3", "inserted: 3, 2"),
						tuple("sam3", "inserted: 3, 3"));
    }

    @Test
    public void mostRecentRequestsByTagAreInTheRightOrder() {
    	assertThat(service.getMostRecentByTag("sam0")).hasSize(0);
    	setupData();
    	assertThat(service.getMostRecentByTag("sam0")).extracting("tag", "body")
				.containsExactly(tuple("sam0", "inserted: 0, 0")); 
    	assertThat(service.getMostRecentByTag("sam1")).extracting("tag", "body")
				.containsExactly(tuple("sam1", "inserted: 1, 0"),
						tuple("sam1", "inserted: 1, 1"));
    	assertThat(service.getMostRecentByTag("sam2")).extracting("tag", "body")
				.containsExactly(tuple("sam2", "inserted: 2, 0"),
						tuple("sam2", "inserted: 2, 1"),
						tuple("sam2", "inserted: 2, 2"));
    	// ... etc ...
    	assertThat(service.getMostRecentByTag("sam8")).hasSize(9);
    	assertThat(service.getMostRecentByTag("sam9")).hasSize(10);
    	// only returns 10 requests even if there are more
    	assertThat(service.getMostRecentByTag("sam10")).hasSize(10);
    }
    
    @Test
    public void popularTagsAreInTheRightOrder() {
    	assertThat(service.getPopularTags()).hasSize(0);
    	setupData();
    	// 210 requests have been added to the datastore BUT only the first 100 count towards popular tags
    	assertThat(service.getPopularTags()).extracting("tag", "count")
				.containsOnly(tuple("sam12", 13),
						tuple("sam11", 12),
						tuple("sam10", 11),
						tuple("sam9", 10),
						tuple("sam13", 9),
						tuple("sam8", 9),
						tuple("sam7", 8),
						tuple("sam6", 7),
						tuple("sam5", 6),
						tuple("sam4", 5));
    	}

    // run this test twice to prove we're not leaking any state across tests
    private void doPersistTest() {
        assertThat(ds.prepare(new Query("CapturedRequest")).countEntities(withLimit(10))).isEqualTo(0);
        assertEquals(0, ds.prepare(new Query("CapturedRequest")).countEntities(withLimit(10)));
        CapturedRequest cr1 = service.persist(aCapturedRequestWithTag("sam"));
        CapturedRequest cr2 = service.persist(aCapturedRequestWithTag("sam"));        
        assertThat(ds.prepare(new Query("CapturedRequest")).countEntities(withLimit(10))).isEqualTo(2);
        // assert that both keys have the form "xxx/sam" but do not match 
        assertThat(cr1.getKey().toString().endsWith("/sam")).isTrue();
        assertThat(cr2.getKey().toString().endsWith("/sam")).isTrue();
        assertThat(cr1.getKey().toString()).isNotEqualTo(cr2.getKey().toString());
    }
    
    private CapturedRequest aCapturedRequestWithTag(String tag) {
    	CapturedRequest cr = new CapturedRequest(tag);
    	cr.setMethod("GET");
    	cr.setHeaders(someHeaders());
    	cr.setUrl("/capture/sam/test");
    	cr.setBody("");
    	return cr;
    }

    private CapturedRequest aCapturedRequestWithTagAndBodyAndTimestamp(String tag, String body, DateTime ts) {
    	CapturedRequest cr = aCapturedRequestWithTag(tag);
    	cr.setTimestamp(ts.toDate());
    	cr.setBody(body);
    	return cr;
    }
    
    private Map<String, String> someHeaders() {
    	Map<String, String> headers = new java.util.HashMap<String, String>();
    	headers.put("header1", "value1");
    	headers.put("header2", "value2");
    	return headers;
    }

    /*
     * Adds 210 requests to the datastore with a triangular structure:
     * * sam0 x 1
     * * sam1 x 2
     * * sam2 x 3
     * * ...
     * * sam19 x 20
     */
    private void setupData() {
    	DateTime dt = new DateTime();
    	for (int i = 0; i < 20; i++) {
			DateTime ts = dt.minusHours(i);
    		for (int j = 0; j < i + 1; j++) {
    			String tag = String.format("sam%s", i);
        		String body = String.format("inserted: %s, %s", i, j);
        		service.persist(aCapturedRequestWithTagAndBodyAndTimestamp(tag, body, ts.minusSeconds(j)));
    		}
    	}
    }
}