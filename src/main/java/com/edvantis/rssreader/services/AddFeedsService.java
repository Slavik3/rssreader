package com.edvantis.rssreader.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.edvantis.rssreader.exception.SyntaxException;
import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.model.Rss;
import com.edvantis.rssreader.repository.RssRepository;
import com.edvantis.rssreader.repository.SourceRepository;

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
	
	@Autowired
	private SourceRepository sourceRepository;

	public List<NewsItem> getNews(String url) throws SyntaxException {
		Rss forObject = restTemplate.getForObject(url, Rss.class);
		NewsItem[] item = forObject.getChannel().getItem();
		List<NewsItem> news = new ArrayList<NewsItem>();
		for (int i = 0; i < forObject.getChannel().getItem().length; i++) {
			NewsItem ig = new NewsItem();
			ig.setTitle(item[i].getTitle());
			ig.setDescription(item[i].getDescription());
			ig.setLink(item[i].getLink());
			ig.setPubDate(item[i].getPubDate());

			URI uri = null;
			try {
				uri = new URI(url);

				String domain = uri.getHost();
				ig.setSource(domain);
				news.add(ig);
			} catch (URISyntaxException e) {
				log.error(e);
				throw new SyntaxException("incorrect URL");
			}
		}
		return news;
	}

	public List<String> getSourceURLs() {
		List<String> sourceURL = new ArrayList<String>();
		for(int i=0; i<sourceRepository.findAll().size(); i++) {
			sourceURL.add(sourceRepository.findAll().get(i).getSourceURL());
		}
		return sourceURL;
	}

	public List<NewsItem> getFeeds() throws SyntaxException {
		List<NewsItem> allNewsFromRss = new ArrayList<NewsItem>();
		for (int i = 0; i < getSourceURLs().size(); i++) {
			List<NewsItem> items = getNews(getSourceURLs().get(i));
			allNewsFromRss.addAll(items);
		}
		return allNewsFromRss;
	}

	public void addFeeds() throws SyntaxException {
		// LOG.info("addFeeds");
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
