package com.edvantis.rssreader.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.repository.RssRepository;
import com.edvantis.rssreader.services.Util;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping(value = "/")
public class RssController extends TimerTask {
	
	 @Autowired
	 RestTemplate restTemplate;

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final RssRepository rssRepository;

	public RssController(RssRepository rssRepository) {
		this.rssRepository = rssRepository;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public NewsItem addNewItem(@RequestBody NewsItem item) {
		LOG.info("Saving item.");
		return rssRepository.save(item);
	}
	//mylondon.news
	//u-news.com.ua
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void addNewItems() throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("Saving item.");
		
		//List<ItemGen> news = Util.getNews_("https://www.mylondon.news/news/?service=rss");
		List<NewsItem> news = Util.getNews("https://www.mylondon.news/news/?service=rss");
		//List<ItemGen> news2 = Util.getNews("http://u-news.com.ua/rss.xml");
		//news.addAll(news2);
		rssRepository.save(news);	
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<NewsItem> getAllItems(@RequestParam(value ="source", required = false) String source) {
		LOG.info("Getting all items.");
		
		if(source!=null) {
			return rssRepository.findBySource(source);
		} else {
			return rssRepository.findAll();
		}
	}

	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	public NewsItem getItem(@PathVariable String itemId) {
		LOG.info("Getting item with ID: {}.", itemId);
		return rssRepository.findOne(itemId);
	}

	@Override
	public void run() {
		LOG.info("run");
		List<NewsItem> allNewsFromRss = new ArrayList<NewsItem>();
		List<NewsItem> news = Util.getNews("https://www.mylondon.news/news/?service=rss");
		List<NewsItem> news2 = Util.getNews("http://u-news.com.ua/rss.xml");
		
		allNewsFromRss.addAll(news);
		allNewsFromRss.addAll(news2);
		List<NewsItem> newsFromDB = null;
		try {
			newsFromDB = rssRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		allNewsFromRss.removeAll(newsFromDB);
		
		rssRepository.save(allNewsFromRss);
	}
	
}