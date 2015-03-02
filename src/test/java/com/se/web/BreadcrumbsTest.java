package com.se.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import org.junit.Test;

public class BreadcrumbsTest {

	@Test
	public void parsingNullFormatReturnsDefaultBreadcrumbs() {
		Breadcrumbs breadcrumbs = Breadcrumbs.parseBreadcrumbs(null);
		assertThat(breadcrumbs.getLinks()).extracting("text", "target")
				.containsOnly(tuple("Home", "/?bc="));
	}

	@Test
	public void parsingEmptyFormatReturnsDefaultBreadcrumbs() {
		Breadcrumbs breadcrumbs = Breadcrumbs.parseBreadcrumbs("");
		assertThat(breadcrumbs.getLinks()).extracting("text", "target")
				.containsOnly(tuple("Home", "/?bc="));
	}
	
	@Test
	public void parsingValidFormatReturnsExpectedBreadcrumbs() {
		Breadcrumbs breadcrumbs = Breadcrumbs.parseBreadcrumbs("h,s,l:sam");
		assertThat(breadcrumbs.getLinks()).extracting("text", "target")
				.containsExactly(tuple("Home", "/?bc="),
						tuple("Getting Started", "/start?bc=h"),
						tuple("Recent Requests [sam]", "/list?tag=sam&bc=h%2Cs"));
	}
	
	@Test
	public void addingBreadcrumbAppendsToTheCollection() {
		// start with the default
		Breadcrumbs breadcrumbs = Breadcrumbs.parseBreadcrumbs(null);
		// add a breadcrumb for start page
		breadcrumbs.addBreadcrumb(new Breadcrumb(Page.START));
		assertThat(breadcrumbs.getLinks()).extracting("text", "target")
				.containsExactly(tuple("Home", "/?bc="),
						tuple("Getting Started", "/start?bc=h"));
		// add a breadcrumb for list page
		breadcrumbs.addBreadcrumb(new Breadcrumb(Page.LIST, "sam"));
		assertThat(breadcrumbs.getLinks()).extracting("text", "target")
				.containsExactly(tuple("Home", "/?bc="),
						tuple("Getting Started", "/start?bc=h"),
						tuple("Recent Requests [sam]", "/list?tag=sam&bc=h%2Cs"));
	}
	
	@Test
	public void breadcrumbsAreProvidedInTheCorrectFormat() {
		// start with the default
		Breadcrumbs breadcrumbs = Breadcrumbs.parseBreadcrumbs(null);
		assertThat(breadcrumbs.getFormat()).isEqualTo("h");
		// add a breadcrumb for start page
		breadcrumbs.addBreadcrumb(new Breadcrumb(Page.START));
		assertThat(breadcrumbs.getFormat()).isEqualTo("h,s");
		// add a breadcrumb for list page
		breadcrumbs.addBreadcrumb(new Breadcrumb(Page.LIST, "sam"));
		assertThat(breadcrumbs.getFormat()).isEqualTo("h,s,l:sam");
	}

	@Test
	public void breadcrumbsAreProvidedInTheCorrectUrlFormat() {
		// start with the default
		Breadcrumbs breadcrumbs = Breadcrumbs.parseBreadcrumbs(null);
		assertThat(breadcrumbs.getUrlFormat()).isEqualTo("h");
		// add a breadcrumb for start page
		breadcrumbs.addBreadcrumb(new Breadcrumb(Page.START));
		assertThat(breadcrumbs.getUrlFormat()).isEqualTo("h%2Cs");
		// add a breadcrumb for list page
		breadcrumbs.addBreadcrumb(new Breadcrumb(Page.LIST, "sam"));
		assertThat(breadcrumbs.getUrlFormat()).isEqualTo("h%2Cs%2Cl%3Asam");
	}
}
