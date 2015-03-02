package com.se.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class BreadcrumbTest {

	@Test
	public void asTokenReturnsValidFormat() {
		Breadcrumb breadcrumb = new Breadcrumb(Page.HOME);
		assertThat(breadcrumb.asToken()).isEqualTo("h");
		breadcrumb = new Breadcrumb(Page.START);
		assertThat(breadcrumb.asToken()).isEqualTo("s");
		breadcrumb = new Breadcrumb(Page.LIST, "sam");
		assertThat(breadcrumb.asToken()).isEqualTo("l:sam");
	}
	
	@Test
	public void parsingValidTokenReturnsExpectedLink() {
		// simple
		Link link = Breadcrumb.parseToken("h").asLink("");
		assertThat(link.getText()).isEqualTo("Home");
		assertThat(link.getTarget()).isEqualTo("/?bc=");
		// less simple
		link = Breadcrumb.parseToken("s").asLink("format");
		assertThat(link.getText()).isEqualTo("Getting Started");
		assertThat(link.getTarget()).isEqualTo("/start?bc=format");
		// even less simple
		link = Breadcrumb.parseToken("l:sam").asLink("format");
		assertThat(link.getText()).isEqualTo("Recent Requests [sam]");
		assertThat(link.getTarget()).isEqualTo("/list?tag=sam&bc=format");
	}
	
	@Test
	public void parsingInvalidTokenReturnsNull() {
		Breadcrumb breadcrumb = Breadcrumb.parseToken("");
		assertThat(breadcrumb).isNull();
		breadcrumb = Breadcrumb.parseToken("bogus");
		assertThat(breadcrumb).isNull();
	}	
}
