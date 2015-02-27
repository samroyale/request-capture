package com.se.data;

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

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
