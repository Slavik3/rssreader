package com.edvantis.rssreader.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.repository.RssRepository;
import com.edvantis.rssreader.services.FeedImporter;

@Resource(name="someName")
@Service
@Configurable
@Component("someName")
public class QuartzJob implements Job {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	/*private final RssRepository rssRepository;

	public QuartzJob(RssRepository rssRepository) {
		this.rssRepository = rssRepository;
	}*/
	
	@Autowired 
	private RssRepository rssRepository;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		LOG.info("run");

		List<NewsItem> allNewsFromRss = new ArrayList<NewsItem>();
		List<NewsItem> mylondon = FeedImporter.getNews("https://www.mylondon.news/news/?service=rss");
		List<NewsItem> uNews = FeedImporter.getNews("http://u-news.com.ua/rss.xml");
		List<NewsItem> bbc = FeedImporter.getNews("https://feeds.bbci.co.uk/newsround/home/rss.xml");

		allNewsFromRss.addAll(mylondon);
		allNewsFromRss.addAll(uNews);
		allNewsFromRss.addAll(bbc);

		if (rssRepository.findAll().size() == 0) {
			rssRepository.save(allNewsFromRss);
		} else {
			List<NewsItem> newsFromDB = rssRepository.findAll();
			Collections.sort(newsFromDB);
			Date lastDate = newsFromDB.get(newsFromDB.size() - 1).getPubDate();
			System.out.println();
			List<NewsItem> newsFromRssForAdd = new ArrayList<NewsItem>();
			for (int i = 0; i < allNewsFromRss.size(); i++) {
				if (newsFromDB.get(i).getPubDate().before(lastDate)) {
					newsFromRssForAdd.add(newsFromDB.get(i));
				}
			}
			rssRepository.save(newsFromRssForAdd);
		}

	}



}
