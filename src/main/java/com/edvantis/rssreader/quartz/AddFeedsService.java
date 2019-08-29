package com.edvantis.rssreader.quartz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.repository.RssRepository;
import com.edvantis.rssreader.services.FeedImporter;

@Service
public class AddFeedsService {
	
	@Autowired
	private RssRepository rssRepository;
	
	@Autowired
	private FeedImporter feedImporter;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	public List<String> getSourceURLs() {
		List<String> sourceURL = null;
		sourceURL.add("https://www.mylondon.news/news/?service=rss");
		sourceURL.add("http://u-news.com.ua/rss.xml");
		sourceURL.add("https://feeds.bbci.co.uk/newsround/home/rss.xml");
		return sourceURL;
	}
	
	public List<NewsItem> getFeeds() {
		List<NewsItem> allNewsFromRss = new ArrayList<NewsItem>();
		for(int i=0; i<getSourceURLs().size(); i++) {
			List<NewsItem> items = feedImporter.getNews(getSourceURLs().get(i));
			allNewsFromRss.addAll(items);
		}
		return allNewsFromRss;
	}
	
	public void addFeeds() {
		LOG.info("addFeeds");
		List<NewsItem> allNewsFromRss = getFeeds();
		if (rssRepository.findAll().size() == 0) {
			rssRepository.saveAll(allNewsFromRss);
		} else {
			List<NewsItem> newsFromDB = rssRepository.findAll();
			Collections.sort(newsFromDB);
			Date lastDate = newsFromDB.get(newsFromDB.size() - 1).getPubDate();
			List<NewsItem> newsFromRssForAdd = new ArrayList<NewsItem>();
			for (int i = 0; i < allNewsFromRss.size(); i++) {
				if (newsFromDB.get(i).getPubDate().before(lastDate)) {
					newsFromRssForAdd.add(newsFromDB.get(i));
				}
			}
			rssRepository.saveAll(newsFromRssForAdd);
		}
	
	}

}
