package com.edvantis.rssreader;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.edvantis.rssreader.controller.RssController;
import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.repository.RssRepository;
import com.edvantis.rssreader.services.FeedImporter;

import static org.mockito.Mockito.*;

 
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
 

@RunWith(MockitoJUnitRunner.class)
public class RssControllerTest {
	
	@Mock
	private RssRepository rssRepository;
	@Mock	
	private FeedImporter feedImporter;
	
	@InjectMocks
	RssController rssController = new RssController();
	
	@Test
    public void testGetDomainName() {
		assertEquals("mylondon.news", FeedImporter.getDomainName("https://www.mylondon.news/news/?service=rss"));
	}
	
	@Test(expected = Exception.class)
	public void testGetDomainNameError() {
		FeedImporter.getDomainName("https://www. mylondon.news/news/?service=rss");
	}
	
	@Test
    public void testGetUserById() {
		NewsItem itemDB = new NewsItem();
		itemDB.setItemId("1");
		itemDB.setTitle("testTitle");
		itemDB.setSource("mylondon.news");
		List<NewsItem> news = new ArrayList<NewsItem>();
		news.add(itemDB);
		when(rssRepository.findBySource("mylondon.news")).thenReturn(news);
		
		List<NewsItem> item = rssController.getAllItems("mylondon.news");
        assertEquals("mylondon.news", item.get(0).getSource());
        assertEquals("testTitle", item.get(0).getTitle());
    }

}
