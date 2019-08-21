package com.edvantis.rssreader;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.model.mylondon.news.Item;
import com.edvantis.rssreader.model.mylondon.news.Rss;

public class RssTest2 {
	
	@Test
	public void testXmlMapping() {
		RestTemplate restTemplate = new RestTemplate();
		Rss forObject = restTemplate.getForObject("https://www.mylondon.news/news/?service=rss", Rss.class);
		//System.out.println(forObject.getChannel().getItem().toString());
		Item[] item = forObject.getChannel().getItem();
		List<NewsItem> news = new ArrayList<NewsItem>();
		for(int i=0; i<forObject.getChannel().getItem().length; i++){
			NewsItem ig = new NewsItem();
			ig.setTitle(item[i].getTitle());
			ig.setDescription(item[i].getDescription());
			ig.setPubDate(item[i].getPubDate());
			ig.setLink(item[i].getLink());
			news.add(ig);
		}
		System.out.println();
	}

}
