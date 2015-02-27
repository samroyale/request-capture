package com.se.web;

public enum Page {

	HOME("h", "Home", "/"),
	START("s", "Getting Started", "/start"),
	LIST("l", "Recent Requests", "/list", "tag"),
	DETAIL("d", "Request Detail", "/detail", "id");
	
	private String id;
	
	private String title;

	private String path;
	
	private String defaultParam;
	
	private Page(String id, String title, String path) {
		this.id = id;
		this.title = title;
		this.path = path;
	}

	private Page(String shortName, String title, String path, String defaultParam) {
		this(shortName, title, path);
		this.defaultParam = defaultParam;
	}

	public static Page findById(String id) {
	    for (Page page: values()) {
	        if (page.getId().equals(id)) {
	            return page;
	        }
	    }
	    return null;
	}
	
	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getPath() {
		return path;
	}

	public String getDefaultParam() {
		return defaultParam;
	}
}
