package com.edvantis.rssreader.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Source {
	private String sourceURL;
	private String title;
	private String description;
	private String link;
	private String pubDate;
	private String source;
	
	public String getSourceURL() {
		return sourceURL;
	}
	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public Source(String sourceURL, String title, String description, String link, String pubDate, String source) {
		super();
		this.sourceURL = sourceURL;
		this.title = title;
		this.description = description;
		this.link = link;
		this.pubDate = pubDate;
		this.source = source;
	}
	
	@Override
	public String toString() {
		return "Source [sourceURL=" + sourceURL + ", title=" + title + ", description=" + description + ", link=" + link
				+ ", pubDate=" + pubDate + ", source=" + source + "]";
	}
	
}
