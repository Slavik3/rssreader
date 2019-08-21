package com.edvantis.rssreader.model.mylondon.news;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Channel {
	private String copyright;
	@JacksonXmlElementWrapper(useWrapping = false)
	private Item[] item;


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

	@Override
	public String toString() {
		return "Channel [copyright=" + copyright + ", item=" + Arrays.toString(item) + "]";
	}

	
	
}