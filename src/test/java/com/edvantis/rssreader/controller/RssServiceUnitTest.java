package com.edvantis.rssreader.controller;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.edvantis.rssreader.BootMongoDBApp;
import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.quartz.AddFeedsService;
import com.edvantis.rssreader.repository.RssRepository;


import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootMongoDBApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class RssServiceUnitTest {
	
	@LocalServerPort
    int randomServerPort;
	
	@Resource
    private RssRepository genericEntityRepository;
	@Autowired
	private AddFeedsService addFeedsService;
	
	List<NewsItem> allNewsFromDB;
 
    @Test
    public void saveItemsToDBTest() {
    	List<NewsItem> allNewsFromRss = addFeedsService.getFeeds();
    	
    	genericEntityRepository.saveAll(allNewsFromRss);
    	
    	allNewsFromDB = genericEntityRepository.findAll();
    	System.out.println("allNewsFromDB==> " + allNewsFromDB);
    	assertTrue(allNewsFromDB.size()>0);
    	/*NewsItem foundEntity = genericEntityRepository.findOne(genericEntity.getId());
  
        assertNotNull(foundEntity);
        assertEquals(genericEntity.getValue(), foundEntity.getValue());*/
    }
    
    @Test
    public void getFeedsRestTest() throws URISyntaxException {
    	RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:" + randomServerPort+ "/feeds";
        URI uri = new URI(baseUrl);
     
        ResponseEntity<NewsItem> result = restTemplate.getForEntity(uri, NewsItem.class);
        System.out.println("==> " + result);
        System.out.println("==> " + result.getBody());
        System.out.println("==> " + result.getBody().getTitle());
        //Verify request succeed
        assertEquals(200, result.getStatusCodeValue());
        //assertTrue(result. size()>0);
        //assertEquals(true, result.getBody().contains("employeeList"));
    	
    	 /*Response response = target("/feeds").request().get();
    	 assertEquals("should return status 200", 200, response.getStatus());
    	 assertNotNull("Should return feed list", response.getEntity().toString());*/
    }

}
