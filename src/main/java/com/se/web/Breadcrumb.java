package com.se.web;

/**
 * A single breadcrumb - see the Breadcrumbs class for more info.
 * 
 * @author seldred
 */
public class Breadcrumb {
	
	private Page page;
	
	private String paramValue;

	public Breadcrumb(Page page) {
		this.page = page;
	}
	
	public Breadcrumb(Page page, String paramValue) {
		this(page);
		this.paramValue = paramValue;
	}
	
	public Link asLink(String breadcrumbFormat) {
		if (paramValue == null) {
			String title = page.getTitle();
			String path = String.format("%s?bc=%s", page.getPath(), breadcrumbFormat);
			return new Link(title, path);
		}
		String title = String.format("%s [%s]", page.getTitle(), paramValue);
		String path = String.format("%s?%s=%s&bc=%s", page.getPath(), page.getDefaultParam(), paramValue, breadcrumbFormat);
		return new Link(title, path);
	}
	
	public String asToken() {
		if (paramValue == null) {
			return page.getId();
		}
		return String.format("%s:%s", page.getId(), paramValue); 
	}
	
	/**
	 * Parses the given token (eg. l:start) where the first part is the ID of a
	 * Page and the second is an optional param value. 
	 * @param token
	 * @return
	 */
	public static Breadcrumb parseToken(String token) {
		String[] bits = token.split(":");
		if (bits.length == 0) {
			return null;
		}
		Page page = Page.findById(bits[0]);
		if (page == null) {
			return null;
		}
		if (bits.length == 1) {
			return new Breadcrumb(page);
		}
		return new Breadcrumb(page, bits[1]);
	}
}
