package com.edvantis.rssreader.services;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.model.Rss;
import com.edvantis.rssreader.repository.RssRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AddFeedsService {
	static Logger log = Logger.getLogger(AddFeedsService.class.getName());
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RssRepository rssRepository;
    
    public List<NewsItem> getNews(String url) {
		Rss forObject = restTemplate.getForObject(url, Rss.class);
		NewsItem[] item = forObject.getChannel().getItem();
		List<NewsItem> news = new ArrayList<NewsItem>();
		for(int i=0; i<forObject.getChannel().getItem().length; i++){
			NewsItem ig = new NewsItem();
			ig.setTitle(item[i].getTitle());
			ig.setDescription(item[i].getDescription());
			ig.setLink(item[i].getLink());
			ig.setPubDate(item[i].getPubDate());
			
			URI uri = null;
			try {
				uri = new URI(url);
			} catch (URISyntaxException e) {
				log.error(e);
			}
	        String domain = uri.getHost();
			ig.setSource(domain);
			news.add(ig);
		}
		return news;
    }
    
    
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
			List<NewsItem> items = getNews(getSourceURLs().get(i));
			allNewsFromRss.addAll(items);
		}
		return allNewsFromRss;
	}
	
	public void addFeeds() {
		//LOG.info("addFeeds");
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
