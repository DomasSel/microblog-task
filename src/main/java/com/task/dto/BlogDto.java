package com.task.dto;

public class BlogDto {
	
	private String title;
	private String body;
	private String authorName;
	private int views;
	
	public BlogDto() {}
	
	public String getTitle() {
	    return title;
	}
	public void setTitle(String title) {
	    this.title = title;
	}
	public String getBody() {
	    return body;
	}
	public void setBody(String body) {
	    this.body = body;
	}
	public String getAuthorName() {
	    return authorName;
	}
	public void setAuthorName(String authorName) {
	    this.authorName = authorName;
	}
	public int getViews() {
	    return views;
	}
	public void setViews(int views) {
	    this.views = views;
	}
}
