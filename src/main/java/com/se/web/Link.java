package com.se.web;

public class Link {

	private String text;
	
	private String target;

	public Link(String text) {
		this.text = text;
	}
	
	public Link(String text, String target) {
		this(text);
		this.target = target;
	}

	public String getText() {
		return text;
	}

	public String getTarget() {
		return target;
	}
}
