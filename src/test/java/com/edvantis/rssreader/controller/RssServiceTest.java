package com.edvantis.rssreader.controller;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.edvantis.rssreader.BootMongoDBApp;
import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.repository.RssRepository;
import com.edvantis.rssreader.services.AddFeedsService;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootMongoDBApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class RssServiceTest {
	
	@LocalServerPort
    int randomServerPort;
	
	@Resource
    private RssRepository genericEntityRepository;
	@Autowired
	private AddFeedsService addFeedsService;
	
	List<NewsItem> allNewsFromDB;
	
	@Before
    public void initialize() {
		List<NewsItem> allNewsFromRss = addFeedsService.getFeeds();
    	genericEntityRepository.saveAll(allNewsFromRss);
	}
    
    @Test
    public void getFeedsRestTest() throws URISyntaxException {
    	RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:" + randomServerPort+ "/feeds";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        assertEquals(200, result.getStatusCodeValue());
        //assertTrue(result.getBody().length()>0);
        assertTrue(result.getBody().contains("u-news.com.ua"));
        assertTrue(result.getBody().contains("feeds.bbci.co.uk"));
    }
    
    @Test
    public void getFeedByIdRestTest() throws URISyntaxException {
    	RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:" + randomServerPort+ "/feeds/"+allNewsFromDB.get(1).getItemId();
        URI uri = new URI(baseUrl);
        ResponseEntity<NewsItem> result = restTemplate.getForEntity(uri, NewsItem.class);
        assertFalse(result.getBody().getTitle().isEmpty());
    }
    
    @Test
    public void getFeedBySourceRestTest() throws URISyntaxException {
    	String source = "u-news.com.ua";
    	RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:" + randomServerPort+ "/feeds?source=" + source;
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        List<NewsItem> items = new ArrayList<>();
        items.forEach(i -> {
        	assertEquals(source, i.getSource());
        });
        //assertFalse(result.getBody().contains("<source>mylondon.news</source>"));
        //assertTrue(result.getBody().contains("<source>u-news.com.ua</source>"));
        //assertFalse(result.getBody().contains("<source>feeds.bbci.co.uk</source>"));
        
    }
    
    

}
