package com.edvantis.rssreader.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.repository.RssRepository;
import com.edvantis.rssreader.services.FeedImporter;

@RestController
@RequestMapping(value = "/")
public class RssController extends TimerTask {
	

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
		List<NewsItem> mylondon = FeedImporter.getNews("https://www.mylondon.news/news/?service=rss");
		List<NewsItem> uNews = FeedImporter.getNews("http://u-news.com.ua/rss.xml");
		List<NewsItem> bbc = FeedImporter.getNews("https://feeds.bbci.co.uk/newsround/home/rss.xml");
		
		allNewsFromRss.addAll(mylondon);
		allNewsFromRss.addAll(uNews);
		allNewsFromRss.addAll(bbc);
		
		if(rssRepository.findAll().size()==0){
			rssRepository.save(allNewsFromRss);
		} else {
			List<NewsItem> newsFromDB = rssRepository.findAll();
			Collections.sort(newsFromDB);
			Date lastDate = newsFromDB.get(newsFromDB.size()-1).getPubDate();
			System.out.println();
			List<NewsItem> newsFromRssForAdd = new ArrayList<NewsItem>();
			for(int i=0; i<allNewsFromRss.size(); i++){
				if(newsFromDB.get(i).getPubDate().before(lastDate)) {//
					newsFromRssForAdd.add(newsFromDB.get(i));
				}
			}
		
			rssRepository.save(newsFromRssForAdd);
		}
		
	}
	
}