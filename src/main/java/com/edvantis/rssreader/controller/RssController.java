package com.edvantis.rssreader.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.edvantis.rssreader.model.ItemGen;
import com.edvantis.rssreader.repository.RssRepository;
import com.edvantis.rssreader.services.Util;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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
	public ItemGen addNewItem(@RequestBody ItemGen item) {
		LOG.info("Saving item.");
		return rssRepository.save(item);
	}
	//mylondon.news
	//u-news.com.ua
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void addNewItems() throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("Saving item.");
		
		//List<ItemGen> news = Util.getNews_("https://www.mylondon.news/news/?service=rss");
		List<ItemGen> news = Util.getNews("https://www.mylondon.news/news/?service=rss");
		//List<ItemGen> news2 = Util.getNews("http://u-news.com.ua/rss.xml");
		//news.addAll(news2);
		rssRepository.save(news);	
	}
	
	private static ItemGen XMLtoPersonExample(String filename) throws Exception {
		URL url = new URL(filename);
		File file = Paths.get(url.toURI()).toFile(); //new File(filename);
        JAXBContext jaxbContext = JAXBContext.newInstance(ItemGen.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (ItemGen) jaxbUnmarshaller.unmarshal(file);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<ItemGen> getAllItems(@RequestParam(value ="source", required = false) String source) {
		LOG.info("Getting all items.");
		
		if(source!=null) {
			return rssRepository.findBySource(source);
		} else {
			return rssRepository.findAll();
		}
	}

	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	public ItemGen getItem(@PathVariable String itemId) {
		LOG.info("Getting item with ID: {}.", itemId);
		return rssRepository.findOne(itemId);
	}

	@Override
	public void run() {
		LOG.info("run");
		List<ItemGen> allNewsFromRss = new ArrayList<ItemGen>();
		List<ItemGen> news = Util.getNews("https://www.mylondon.news/news/?service=rss");
		List<ItemGen> news2 = Util.getNews("http://u-news.com.ua/rss.xml");
		
		allNewsFromRss.addAll(news);
		allNewsFromRss.addAll(news2);
		List<ItemGen> newsFromDB = null;
		try {
			newsFromDB = rssRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		allNewsFromRss.removeAll(newsFromDB);
		
		rssRepository.save(allNewsFromRss);
	}
	
}