package com.edvantis.rssreader;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.edvantis.rssreader.controller.RssController;
import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.quartz.AddFeedsService;
import com.edvantis.rssreader.repository.RssRepository;
import com.edvantis.rssreader.services.FeedImporter;

@RunWith(MockitoJUnitRunner.class)
public class FeedsTest {
	@Mock
	AddFeedsService addFeedsService;
	
	@InjectMocks
	AddFeedsService addFeedsSerice = new AddFeedsService();
	
	@Mock	
	private FeedImporter feedImporter;
	
	@Mock
	private RssRepository rssRepository;
	
	@Test
    public void testGetFeeds() {
		NewsItem item = new NewsItem();
		item.setItemId("1");
		item.setTitle("testTitle");
		item.setSource("mylondon.news");
		Date date = new Date();
		item.setPubDate(date);
		List<NewsItem> news = new ArrayList<NewsItem>();
		news.add(item);
		
		NewsItem itemDB = new NewsItem();
		itemDB.setItemId("1");
		itemDB.setTitle("testTitle");
		itemDB.setPubDate(date);
		itemDB.setSource("mylondon.news");
		List<NewsItem> newsDB = new ArrayList<NewsItem>();
		newsDB.add(itemDB);
		
		when(feedImporter.getNews("https://www.mylondon.news/news/?service=rss")).thenReturn(news);
		when(rssRepository.findAll()).thenReturn(newsDB);
		//List<NewsItem> allNewsFromRss = addFeedsService.getFeeds();
		
		//AddFeedsService a = new AddFeedsService();
		assertEquals(1, addFeedsSerice.getFeeds().size());
		//
		addFeedsSerice.addFeeds();
	}

}
