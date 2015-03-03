package com.se.data;

/**
 * Simple value object that holds a count for the given tag.
 *   
 * @author seldred
 */
public class TagCount {

	private String tag;
	
	private int count;
	
	public TagCount(String tag, int count) {
		this.tag = tag;
		this.count = count;
	}

	public String getTag() {
		return tag;
	}

	public int getCount() {
		return count;
	}
}
