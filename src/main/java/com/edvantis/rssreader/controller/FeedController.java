package com.edvantis.rssreader.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edvantis.rssreader.annotation.LogExecutionTime;
import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.repository.RssRepository;
import com.edvantis.rssreader.repository.RssRepository2;
import com.edvantis.rssreader.services.AddFeedsService;
import com.itextpdf.html2pdf.HtmlConverter;
import com.pdfcrowd.Pdfcrowd;
import com.wordnik.swagger.annotations.Api;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


import java.io.FileInputStream;
import java.io.OutputStream;
 
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
 
import javax.servlet.ServletContext;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
 


@RestController
@RequestMapping(value = "/feeds")
@Api(value = "feeds", description = "Feeds API")
public class FeedController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private RssRepository rssRepository;
	
	@Autowired
	private RssRepository2 rssRepository2;
	
	@Autowired
	private AddFeedsService addFeedsService;

	@LogExecutionTime
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public NewsItem addNewItem(@RequestBody NewsItem item) {
		LOG.info("Saving item.");
		return rssRepository.save(item);
	}

	@LogExecutionTime
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<NewsItem> getAllItems(@RequestParam(value = "source", required = false) String source) {
		LOG.info("Getting all items.");
		if (source == "") {
			return rssRepository.findAll();
		} else if (source != null) {
			return rssRepository.findBySource(source);
		} else {
			return rssRepository.findAll();
		}
	}

	@LogExecutionTime
	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	public Optional<NewsItem> getItem(@PathVariable String itemId) {
		LOG.info("Getting item with ID: {}.", itemId);
		return rssRepository.findById(itemId);
	}

	@LogExecutionTime
	@RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteItem(@PathVariable String itemId) {
		rssRepository.deleteById(itemId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@LogExecutionTime
	@RequestMapping(value = "/srcOfNews", method = RequestMethod.GET)
	public Stream<String> getSrc() {
		/*Set<String> src = new HashSet<String>();
		List<NewsItem> ni = rssRepository.findAll();
		for(int i=0; i<ni.size(); i++){
			src.add(ni.get(i).getSource());
		}
		return src;*/

		return rssRepository.findAll().stream().map(item -> item.getSource()).distinct();
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(@RequestParam(value = "source", required = false) String source) {
		LOG.info("upload");
		LOG.info("source==> " + source);
		if (source == null || source.equals("undefined")) {
			addFeedsService.addFeeds();
		} else {
			addFeedsService.addFeeds(source);
		}

	}
	
	@RequestMapping(value = "/openArticleFromDB/{id}", method = RequestMethod.GET)
	public String openArticleFromDB(@PathVariable Integer id) throws IOException {
		LOG.info("openArticleFromDB");
		System.out.println("id==> " + id);
		String htmlBodyDetailFromDB = rssRepository2.findById(id).get().getHtml_body_detail();
		System.out.println(htmlBodyDetailFromDB);
		String htmlBodyDetail = null;
		if(htmlBodyDetailFromDB == null) {
			//TODO get from site and save to DB
			NewsItem ni = rssRepository2.findArticleById(id);
			
			System.out.println(ni);
			htmlBodyDetail = ni.getArticle(ni.getLink());
			ni.setHtml_body_detail(htmlBodyDetail);
			rssRepository.save(ni);
		}
		return htmlBodyDetail;
	}
	
	@RequestMapping(value = "/savePDF/{id}", method = RequestMethod.GET)
	public void savePDF(@PathVariable Integer id) throws IOException {
		LOG.info("savePDF");
		String link = rssRepository2.findById(id).get().getLink();
		String title = rssRepository2.findById(id).get().getTitle();
		System.out.println(link);
		
		Document doc = Jsoup.connect(link).get();
		Elements element = doc.getElementsByAttributeValue("class", "newsround-story-body__content").get(0).children();
		String body = element.text();
		HtmlConverter.convertToPdf(body, new FileOutputStream(id+".pdf"));
		
		Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + "D:\\rssreader\\"+id+".pdf");
		
	}
	

}