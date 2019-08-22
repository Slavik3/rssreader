package com.edvantis.rssreader;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.model.bbc.com.Item;
import com.edvantis.rssreader.model.bbc.com.Rss;

public class RssTest2 {
	
	@Test
	public void testXmlMapping() {
		RestTemplate restTemplate = new RestTemplate();
		//https://www.huffpost.com/section/asian-voices/feed 403
		Rss forObject = restTemplate.getForObject("https://feeds.bbci.co.uk/newsround/home/rss.xml", Rss.class);
		//System.out.println(forObject.getChannel().getItem().toString());
		Item[] item = forObject.getChannel().getItem();
		List<NewsItem> news = new ArrayList<NewsItem>();
		for(int i=0; i<forObject.getChannel().getItem().length; i++){
			NewsItem ig = new NewsItem();
			ig.setTitle(item[i].getTitle());
			ig.setDescription(item[i].getDescription());
			ig.setPubDate(item[i].getPubDate());
			System.out.println(item[i].getPubDate());
			ig.setLink(item[i].getLink());
			news.add(ig);
		}
		System.out.println();
	}

}
