package com.se.web;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class BreadcrumbsTest {

	@Test
	public void parsingEmptyBreadcrumbsFormatReturnsDefaultBreadcrumbs() {
		Breadcrumbs breadcrumbs  = Breadcrumbs.parseBreadcrumbs("");
		List<Link> links = breadcrumbs.getLinks();
		Assert.assertEquals(1, links.size());
	}
}
