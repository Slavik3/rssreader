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
	@Column(name = "pub_date")
	private String pubDate;//TODO ?
	private String hostname;
	private Boolean isActive;
	
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
		return pubDate;
	}
	public void setPub_date(String pub_date) {
		this.pubDate = pub_date;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public Source() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Source(int id, String sourceURL, String title, String description, String link, String pubDate,
			String hostname, Boolean isActive) {
		super();
		this.id = id;
		this.sourceURL = sourceURL;
		this.title = title;
		this.description = description;
		this.link = link;
		this.pubDate = pubDate;
		this.hostname = hostname;
		this.isActive = isActive;
	}
	
	@Override
	public String toString() {
		return "Source [id=" + id + ", sourceURL=" + sourceURL + ", title=" + title + ", description=" + description
				+ ", link=" + link + ", pubDate=" + pubDate + ", hostname=" + hostname + ", isActive=" + isActive + "]";
	}
	
}
