package com.edvantis.rssreader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.edvantis.rssreader.model.ItemGen;
import com.edvantis.rssreader.model.unews.com.ua.Item;
import com.edvantis.rssreader.model.unews.com.ua.Rss;

public class RssTest {

	@Test
	public void testXmlMapping() {
		
		RestTemplate restTemplate = new RestTemplate();
		Rss forObject = restTemplate.getForObject("http://u-news.com.ua/rss.xml", Rss.class);
		//System.out.println(forObject.getChannel().getItem().toString());
		Item[] item = forObject.getChannel().getItem();
		List<ItemGen> news = new ArrayList<ItemGen>();
		for(int i=0; i<forObject.getChannel().getItem().length; i++){
			ItemGen ig = new ItemGen();
			ig.setTitle(item[i].getTitle());
			ig.setDescription(item[i].getDescription());
			ig.setLink(item[i].getLink());
			ig.setPubDate(item[i].getPubDate());
			ig.setSource("u-news.com.ua");
			news.add(ig);
			System.out.println(item[i].getDescription());
		}
		
		Map forMap = restTemplate.getForObject("http://u-news.com.ua/rss.xml", Map.class);
		System.out.println(forMap);
	}
	
	
}
