package com.edvantis.rssreader.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.edvantis.rssreader.exception.SyntaxException;
import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.repository.RssRepository;
import com.edvantis.rssreader.repository.SourceRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.w3c.dom.Element;

@Service
public class AddFeedsService {
	static Logger log = Logger.getLogger(AddFeedsService.class.getName());

	@Autowired
	private RssRepository rssRepository;
	
	@Autowired
	private SourceRepository sourceRepository;
	
	public List<NewsItem> getNews(String url) throws SyntaxException {
		List<NewsItem> news = new ArrayList<NewsItem>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(url);
			String title = sourceRepository.findBySourceURL(url).getTitle();
			String description = sourceRepository.findBySourceURL(url).getDescription();
			String link = sourceRepository.findBySourceURL(url).getLink();
			String pubDate = sourceRepository.findBySourceURL(url).getPubDate();
			
			NodeList errNodes = doc.getElementsByTagName("item");
			if (errNodes.getLength() > 0) {
				for (int i = 0; i < errNodes.getLength(); i++) {
					NewsItem item = new NewsItem();
					Element element = (Element) errNodes.item(i);
					item.setTitle(element.getElementsByTagName(title).item(0).getTextContent());
					item.setDescription(element.getElementsByTagName(description).item(0).getTextContent());
					item.setLink(element.getElementsByTagName(link).item(0).getTextContent());
					DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
					item.setPubDate(formatter.parse(element.getElementsByTagName(pubDate).item(0).getTextContent()));

					URI uri = null;
					uri = new URI(url);
					String domain = uri.getHost();
					item.setSource(domain);
					news.add(item);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
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
