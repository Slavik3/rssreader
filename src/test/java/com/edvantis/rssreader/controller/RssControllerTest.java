package com.edvantis.rssreader.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.edvantis.rssreader.BootMongoDBApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.edvantis.rssreader.BootMongoDBApp;
import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.repository.RssRepository;
import com.edvantis.rssreader.services.AddFeedsService;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BootMongoDBApp.class })
@WebAppConfiguration
public class RssControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private RssController userController;
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void createFeedsRestTest() throws Exception {
		String json = "{\"id\" : \"1\", "
				+ "\"title\" : \"Police want to find this man suspected of sexual assault on Clapton bus\", "
				+ "\"link\" : \"https://www.mylondon.news/news/east-london-news/police-want-find-man-suspected-16883138\", \"source\" : \"mylondon.news\"}";
		String json2 = "{\"id\" : \"2\", "
				+ "\"title\" : \"Eerie pictures show the sunken South London ship where the Beatles once played\", "
				+ "\"link\" : \"https://www.mylondon.news/news/east-london-news/eerie-pictures-show-sunken-south-16870371\", \"source\" : \"mylondon.news\"}";
		String json3 = "{\"id\" : \"3\", "
				+ "\"title\" : \"Amber Rudd: Cabinet minister quits her job in the government\", "
				+ "\"link\" : \"http://www.bbc.co.uk/newsround/49626593\", \"source\" : \"bbc.co.uk\"}";
		this.mockMvc.perform(post("/feeds/create").contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		this.mockMvc.perform(post("/feeds/create").contentType(MediaType.APPLICATION_JSON).content(json2)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		this.mockMvc.perform(post("/feeds/create").contentType(MediaType.APPLICATION_JSON).content(json3)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void getFeedsRestTest() throws Exception {
		this.mockMvc.perform(get("/feeds"))
				.andExpect(jsonPath("$[0].title").value("Police want to find this man suspected of sexual assault on Clapton bus"))
				.andExpect(jsonPath("$[1].title").value("Eerie pictures show the sunken South London ship where the Beatles once played"))
				.andExpect(jsonPath("$[2].title").value("Amber Rudd: Cabinet minister quits her job in the government"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getFeedBySourceRestTest() throws Exception {
		this.mockMvc.perform(get("/feeds?source=mylondon.news"))
				.andExpect(jsonPath("$[0].title").value("Police want to find this man suspected of sexual assault on Clapton bus"))
				.andExpect(jsonPath("$[1].title").value("Eerie pictures show the sunken South London ship where the Beatles once played"))
				.andExpect(status().isOk());
	}

}