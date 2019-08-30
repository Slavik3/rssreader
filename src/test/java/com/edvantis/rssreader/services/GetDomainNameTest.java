package com.edvantis.rssreader.services;

import static org.junit.Assert.*;


import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.edvantis.rssreader.controller.RssController;
import com.edvantis.rssreader.repository.RssRepository;
import com.edvantis.rssreader.services.FeedImporter;
 
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
 

@RunWith(MockitoJUnitRunner.class)
public class GetDomainNameTest {
	
	@Mock
	private RssRepository rssRepository;
	@Mock	
	private FeedImporter feedImporter;
	
	@InjectMocks
	RssController rssController = new RssController();
	
	@Test
    public void testGetDomainName() {
		assertEquals("mylondon.news", FeedImporter.getDomainName("https://www.mylondon.news/news/?service=rss"));
	}
	
	@Test(expected = Exception.class)
	public void testGetDomainNameError() {
		FeedImporter.getDomainName("https://www. mylondon.news/news/?service=rss");
	}
	

}
