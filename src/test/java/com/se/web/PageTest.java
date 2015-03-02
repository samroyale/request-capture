package com.se.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PageTest {

	@Test
	public void pagesCanBeFoundById() {
		Page page = Page.findById("h");
		assertThat(page.getTitle()).isEqualTo("Home");
		assertThat(page.getPath()).isEqualTo("/");
		page = Page.findById("s");
		assertThat(page.getTitle()).isEqualTo("Getting Started");
		assertThat(page.getPath()).isEqualTo("/start");
		page = Page.findById("l");
		assertThat(page.getTitle()).isEqualTo("Recent Requests");
		assertThat(page.getPath()).isEqualTo("/list");
		page = Page.findById("d");
		assertThat(page.getTitle()).isEqualTo("Request Detail");
		assertThat(page.getPath()).isEqualTo("/detail");
	}
}
