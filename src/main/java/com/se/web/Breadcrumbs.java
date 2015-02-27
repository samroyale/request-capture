package com.se.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Breadcrumbs {

	private static final String URL_ENCODING = "UTF-8";
	
	private static final List<Breadcrumb> DEFAULT_BREADCRUMBS = Arrays.asList(new Breadcrumb(Page.HOME));
		
	private List<Breadcrumb> breadcrumbs;

	private Breadcrumbs() {
		this.breadcrumbs = new ArrayList<>();
	}

	private Breadcrumbs(List<Breadcrumb> breadcrumbs) {
		this.breadcrumbs = breadcrumbs;
	}

	public static Breadcrumbs parseBreadcrumbs(String breadcrumbsFormat) {
		if ((breadcrumbsFormat == null) || (breadcrumbsFormat.isEmpty())) {
			return new Breadcrumbs(DEFAULT_BREADCRUMBS);
		}
		List<Breadcrumb> myBreadcrumbs = new ArrayList<Breadcrumb>();
		String[] breadcrumbTokens = breadcrumbsFormat.split(",");
		for (String token: breadcrumbTokens) {
			Breadcrumb breadcrumb = Breadcrumb.parseToken(token);
			if (breadcrumb != null) {
				myBreadcrumbs.add(breadcrumb);
			}
		}
		return new Breadcrumbs(myBreadcrumbs);
	}
	
	public void addBreadcrumb(Breadcrumb breadcrumb) {
		breadcrumbs.add(breadcrumb);
	}

	public List<Link> getLinks() {
		List<Link> links = new ArrayList<>();
		Breadcrumbs myBreadcrumbs = new Breadcrumbs();
		for (Breadcrumb breadcrumb: breadcrumbs) {
			links.add(breadcrumb.asLink(myBreadcrumbs.getUrlFormat()));
			myBreadcrumbs.addBreadcrumb(breadcrumb);
		}
		return links;
	}
	
	public String getFormat() {
		StringBuilder formatBuilder = new StringBuilder();
		Iterator<Breadcrumb> i = breadcrumbs.iterator();
		while (i.hasNext()) {
			Breadcrumb breadcrumb = i.next();
			formatBuilder.append(breadcrumb.asToken());
			if (i.hasNext()) {
				formatBuilder.append(',');
			}
		}
		return formatBuilder.toString();
	}
	
	public String getUrlFormat() {
		try {
			return URLEncoder.encode(getFormat(), URL_ENCODING);
		}
		catch (UnsupportedEncodingException e) {
			// nothing we can do here!
		}
		return null;
	}
}
