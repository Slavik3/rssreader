package com.edvantis.rssreader.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.edvantis.rssreader.BootMongoDBApp;

import org.springframework.test.context.web.WebAppConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BootMongoDBApp.class })
@WebAppConfiguration
public class FeedControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		
		String json = "{\"id\" : \"1\", "
				+ "\"title\" : \"Police want to find this man suspected of sexual assault on Clapton bus\", "
				+ "\"pubDate\" : \"2019-11-15T10:31:30.000+0000\", "
				+ "\"link\" : \"https://www.mylondon.news/news/east-london-news/police-want-find-man-suspected-16883138\", \"source\" : \"mylondon.news\"}";
		String json2 = "{\"id\" : \"2\", "
				+ "\"title\" : \"Eerie pictures show the sunken South London ship where the Beatles once played\", "
				+ "\"pubDate\" : \"2019-11-14T10:31:30.000+0000\", "
				+ "\"link\" : \"https://www.mylondon.news/news/east-london-news/eerie-pictures-show-sunken-south-16870371\", \"source\" : \"mylondon.news\"}";
		String json3 = "{\"id\" : \"3\", "
				+ "\"title\" : \"Amber Rudd: Cabinet minister quits her job in the government\", "
				+ "\"pubDate\" : \"2019-11-16T10:31:30.000+0000\", "
				+ "\"link\" : \"http://www.bbc.co.uk/newsround/49626593\", \"source\" : \"bbc.co.uk\"}";
		this.mockMvc.perform(post("/feeds/create").contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		this.mockMvc.perform(post("/feeds/create").contentType(MediaType.APPLICATION_JSON).content(json2)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		this.mockMvc.perform(post("/feeds/create").contentType(MediaType.APPLICATION_JSON).content(json3)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	

	@Test
	public void getAllFeedsRestTest() throws Exception {
		this.mockMvc.perform(get("/feeds/all"))
		.andExpect(jsonPath("content.[0].title").value("Police want to find this man suspected of sexual assault on Clapton bus"))
		.andExpect(jsonPath("content.[1].title").value("Eerie pictures show the sunken South London ship where the Beatles once played"))
		.andExpect(jsonPath("content.[2].title").value("Amber Rudd: Cabinet minister quits her job in the government"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getFeedByParametersRestTest() throws Exception {
		this.mockMvc.perform(get("/feeds")
				.param("title", "Police want to find this man suspected of sexual assault on Clapton bus")
				.param("source", "mylondon.news")
				.param("dateFrom", "2019-11-15")
				.param("dateTo", "2019-11-16").param("sortTableByPublicationDate", "ASC"))
				.andExpect(jsonPath("content.[0].title").value("Police want to find this man suspected of sexual assault on Clapton bus"))
				.andExpect(jsonPath("content.[1].title").doesNotExist())
				.andExpect(status().isOk());
	}
	
	@Test
	public void uploadTest() throws Exception {
		this.mockMvc.perform(post("/feeds/upload"))
		.andExpect(status().isOk());
	}
	
	

}
