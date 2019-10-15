package com.edvantis.rssreader.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.edvantis.rssreader.BootMongoDBApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BootMongoDBApp.class })
@WebAppConfiguration
public class SourceControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void createSourceRestTest() throws Exception {
		String json = "{\"sourceURL\" : \"https://hnrss.org/newest\", "
				+ "\"title\" : \"title\", "
				+ "\"description\" : \"description\", "
				+ "\"link\" : \"link\", "
				+ "\"pubDate\" : \"pubDate\"}";
		
		this.mockMvc.perform(post("/addSource").contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		this.mockMvc.perform(get("/getSource").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].sourceURL").value("https://hnrss.org/newest"))
		.andExpect(jsonPath("$[0].title").value("title"))
		.andExpect(jsonPath("$[0].description").value("description"))
		.andExpect(jsonPath("$[0].link").value("link"))
		.andExpect(jsonPath("$[0].pubDate").value("pubDate"));
		
	}
	
}
