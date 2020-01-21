package com.edvantis.rssreader.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.springframework.web.util.NestedServletException;

import com.edvantis.rssreader.Rssreader;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Rssreader.class })
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
	public void CRUD_SourceRestTest() throws Exception {
		String json = "{\"sourceURL\" : \"https://www.mylondon.news/news/?service=rss\", "
				+ "\"title\" : \"title\", "
				+ "\"description\" : \"description\", "
				+ "\"link\" : \"link\", "
				+ "\"pubDate\" : \"pubDate\"}";
		
		this.mockMvc.perform(post("/sources/add").contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		this.mockMvc.perform(get("/sources/getAll").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].sourceURL").value("https://www.mylondon.news/news/?service=rss"))
		.andExpect(jsonPath("$[0].title").value("title"))
		.andExpect(jsonPath("$[0].id").value("1"))
		.andExpect(jsonPath("$[0].description").value("description"))
		.andExpect(jsonPath("$[0].link").value("link"))
		.andExpect(jsonPath("$[0].pubDate").value("pubDate"));
		
		String json2 = "{"
				+ "\"id\" : \"1\", "
				+ "\"sourceURL\" : \"https://www.mylondon.news/news/?service=rss\", "
				+ "\"title\" : \"title2\", "
				+ "\"description\" : \"description2\", "
				+ "\"link\" : \"link2\", "
				+ "\"pubDate\" : \"pubDate2\"}";
		
		this.mockMvc.perform(put("/sources/1").contentType(MediaType.APPLICATION_JSON).content(json2)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		this.mockMvc.perform(get("/sources/getAll").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].sourceURL").value("https://www.mylondon.news/news/?service=rss"))
		.andExpect(jsonPath("$[0].title").value("title2"))
		.andExpect(jsonPath("$[0].id").value("1"))
		.andExpect(jsonPath("$[0].description").value("description2"))
		.andExpect(jsonPath("$[0].link").value("link2"))
		.andExpect(jsonPath("$[0].pubDate").value("pubDate2"));
		
		
		this.mockMvc.perform(delete("/sources/1"));
		this.mockMvc.perform(get("/sources/getAll").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}
	
	
	@Test
	public void createSourceExRestTest() throws Exception {
		String json = "{\"sourceURL\" : \"https://hnrss.org/newest\", "
				+ "\"title\" : \"title\", "
				+ "\"description\" : \"description\", "
				+ "\"link\" : \"link\", "
				+ "\"pubDate\" : \"pubDate\"}";
		try {
			this.mockMvc.perform(post("/sources/add").contentType(MediaType.APPLICATION_JSON).content(json)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().is5xxServerError());
		} catch (NestedServletException e) {
		}
		
		
	}
	
}
