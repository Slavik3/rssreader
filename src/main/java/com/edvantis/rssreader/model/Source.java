package com.edvantis.rssreader.model;

import javax.persistence.*;



@Table(name = "source")
@Entity
public class Source {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String sourceURL;
	private String title;
	private String description;
	private String link;
	private String pub_date;//TODO ?
	private String hostname;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
		return pub_date;
	}
	public void setPub_date(String pub_date) {
		this.pub_date = pub_date;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	public Source() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Source(int id, String sourceURL, String title, String description, String link, String pub_date,
			String hostname) {
		super();
		this.id = id;
		this.sourceURL = sourceURL;
		this.title = title;
		this.description = description;
		this.link = link;
		this.pub_date = pub_date;
		this.hostname = hostname;
	}
	
	@Override
	public String toString() {
		return "Source [id=" + id + ", sourceURL=" + sourceURL + ", title=" + title + ", description=" + description
				+ ", link=" + link + ", pubDate=" + ", hostname=" + hostname + "]";
	}
	
	
	
}
