package com.edvantis.rssreader.controller;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.edvantis.rssreader.BootMongoDBApp;
import com.edvantis.rssreader.filter.SimpleCORSFilter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BootMongoDBApp.class })
@WebAppConfiguration
public class FilterTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	FilterChain chain;
	@Mock
	FilterConfig fc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@org.junit.Test
	public void test() throws Exception {
		new SimpleCORSFilter().destroy();
		new SimpleCORSFilter().doFilter(request, response, chain);
		new SimpleCORSFilter().init(fc);
	}

}
