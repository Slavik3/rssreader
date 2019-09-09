package com.edvantis.rssreader.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Channel {
	@JacksonXmlElementWrapper(useWrapping = false)
	private NewsItem[] item;

	public NewsItem[] getItem() {
		return item;
	}

	public void setItem(NewsItem[] item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "Channel [item=" + Arrays.toString(item) + "]";
	}

}