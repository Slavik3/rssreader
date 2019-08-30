package com.edvantis.rssreader.quartz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		File file = new File("src/main/resources/sourceURLs");
		String absolutePath = file.getAbsolutePath();
		List<String> sourceURL = null;
        try (Stream<String> lines = Files.lines(Paths.get(absolutePath))) {
        	sourceURL = lines.collect(Collectors.toList());
        } catch (IOException e) {
			e.printStackTrace();
		}
        return sourceURL;
	}
	
	public List<NewsItem> getFeeds() {
		List<NewsItem> allNewsFromRss = new ArrayList<NewsItem>();
		for(int i=1; i<getSourceURLs().size(); i++) {
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
