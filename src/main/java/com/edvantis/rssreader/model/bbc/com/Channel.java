package com.edvantis.rssreader.model.bbc.com;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Channel {
	private String copyright;
	@JacksonXmlElementWrapper(useWrapping = false)
	private Item[] item;

	private String lastBuildDate;

	private String link;

	private String description;

	private String generator;

	private String language;

	private String title;

	private String ttl;

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public Item[] getItem() {
		return item;
	}

	public void setItem(Item[] item) {
		this.item = item;
	}

	public String getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTtl() {
		return ttl;
	}

	public void setTtl(String ttl) {
		this.ttl = ttl;
	}

	@Override
	public String toString() {
		return "ClassPojo [copyright = " + copyright + ", item = " + item + ", lastBuildDate = " + lastBuildDate
				+ ", link = " + link + ", description = " + description + ", generator = " + generator + ", language = "
				+ language + ", title = " + title + ", ttl = " + ttl + "]";
	}
}