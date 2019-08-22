package com.edvantis.rssreader.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.model.unews.com.ua.Item;
import com.edvantis.rssreader.model.unews.com.ua.Rss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class FeedImporter {
	
	
    public static String getDomainName(String url) {
        URI uri = null;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
    
    
    public static List<NewsItem> getNews(String url) {
    	RestTemplate restTemplate = new RestTemplate();
		Rss forObject = restTemplate.getForObject(url, Rss.class);
		Item[] item = forObject.getChannel().getItem();
		List<NewsItem> news = new ArrayList<NewsItem>();
		for(int i=0; i<forObject.getChannel().getItem().length; i++){
			NewsItem ig = new NewsItem();
			ig.setTitle(item[i].getTitle());
			ig.setDescription(item[i].getDescription());
			ig.setLink(item[i].getLink());
			ig.setPubDate(item[i].getPubDate());
			ig.setSource(getDomainName(url));
			news.add(ig);
		}
		return news;
    }
    
}
