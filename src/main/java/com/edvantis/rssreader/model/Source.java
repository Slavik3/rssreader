package com.edvantis.rssreader.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class Source {
	
	@JsonProperty("_id")
	@Id
	private String id;
	private String sourceURL;
	private String title;
	private String description;
	private String link;
	private String pubDate;
	private String hostname;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	public Source(String id, String sourceURL, String title, String description, String link, String pubDate,
			String hostname) {
		super();
		this.id = id;
		this.sourceURL = sourceURL;
		this.title = title;
		this.description = description;
		this.link = link;
		this.pubDate = pubDate;
		this.hostname = hostname;
	}
	
	@Override
	public String toString() {
		return "Source [id=" + id + ", sourceURL=" + sourceURL + ", title=" + title + ", description=" + description
				+ ", link=" + link + ", pubDate=" + pubDate + ", hostname=" + hostname + "]";
	}
	
	
	
}
