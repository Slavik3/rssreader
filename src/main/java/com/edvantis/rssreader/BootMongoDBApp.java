package com.edvantis.rssreader;

import java.util.Timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.edvantis.rssreader.controller.RssController;
import com.edvantis.rssreader.repository.RssRepository;

@SpringBootApplication
public class BootMongoDBApp {
	private final RssRepository rssRepository;

	public BootMongoDBApp(RssRepository rssRepository) {
		this.rssRepository = rssRepository;
		Timer timer = new Timer();
		timer.schedule(new RssController(rssRepository), 0, 10000);// 10s
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BootMongoDBApp.class, args);
	}
	
	@Bean
   public RestTemplate getRestTemplate() {
      return new RestTemplate();
   }
}