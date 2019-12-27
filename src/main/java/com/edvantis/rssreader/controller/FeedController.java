package com.edvantis.rssreader.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.edvantis.rssreader.services.AddFeedsService;
import com.edvantis.rssreader.specification.NewsWithDateFrom;
import com.edvantis.rssreader.specification.NewsWithDateTo;
import com.edvantis.rssreader.specification.NewsWithSource;
import com.edvantis.rssreader.specification.NewsWithTitle;
import com.itextpdf.html2pdf.HtmlConverter;
import com.wordnik.swagger.annotations.Api;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;


@RestController
@RequestMapping(value = "/feeds")
@Api(value = "feeds", description = "Feeds API")
public class FeedController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private RssRepository rssRepository;
	
	@Autowired
	private AddFeedsService addFeedsService;
	
	final int size = 300;

	@LogExecutionTime
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public NewsItem addNewItem(@RequestBody NewsItem item) {
		LOG.info("Saving item.");
		return rssRepository.save(item);
	}

	@LogExecutionTime
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Page<NewsItem> getAllItems(
			@RequestParam(value = "source", required = false) String source,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "sortTableByPublicationDate", required = false) String sortTableByPublicationDate,
			@RequestParam(value = "dateFrom", required = false) String dateFrom,
			@RequestParam(value = "dateTo", required = false) String dateTo, @RequestParam(defaultValue = "0") int page, Pageable pageable)
			throws ParseException {
		LOG.info("Getting all items.");
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		
		Date dateFromD = null; 
		Date dateToD = null;
		if(!dateFrom.equals("undefined")) {
			dateFromD = formatter1.parse(dateFrom);
		}
		if(!dateTo.equals("undefined")) {
			dateToD = formatter1.parse(dateTo);
		}
		Specification<NewsItem> spec = Specifications.where(new NewsWithTitle(title))
                .and(new NewsWithSource(source))
                .and(new NewsWithDateFrom(dateFromD))
                .and(new NewsWithDateTo(dateToD));
		rssRepository.findAll(PageRequest.of(page, size, Sort.by(Direction.DESC, "pubDate")));
		Page<NewsItem> ttt = rssRepository.findAll(spec, pageable);
		return rssRepository.findAll(spec, pageable);//pageable	
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Page<NewsItem> getAllItems(@RequestParam(defaultValue = "0") int page) throws ParseException {
		return rssRepository.findAll(PageRequest.of(page, size));
	}
	
	@RequestMapping(value = "/sort", method = RequestMethod.GET)
	public Page<NewsItem> getAllItemsSort(@RequestParam(defaultValue = "0") int page, 
			@RequestParam(value = "sortTableByPublicationDate", required = false) String sortTableByPublicationDate) throws ParseException {
		System.out.println("sort");
		if (sortTableByPublicationDate.equals("ASC")) {
			System.out.println("ASC");
			return rssRepository.findAll(PageRequest.of(page, size, Sort.by(Direction.ASC, "pubDate")));
		}
		else {
			System.out.println("DESC");
			return rssRepository.findAll(PageRequest.of(page, size, Sort.by(Direction.DESC, "pubDate")));
		}
		
	}


	@LogExecutionTime
	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	public Optional<NewsItem> getItem(@PathVariable Integer itemId) {
		LOG.info("Getting item with ID: {}.", itemId);
		return rssRepository.findById(itemId);
	}

	@LogExecutionTime
	@RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteItem(@PathVariable Integer itemId) {
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
		String htmlBodyDetailFromDB = rssRepository.findById(id).get().getHtml_body_detail();
		String htmlBodyDetail = null;
		if(htmlBodyDetailFromDB == null) {
			NewsItem ni = rssRepository.findArticleById(id);
			htmlBodyDetail = ni.getArticle(ni.getLink());
			ni.setHtml_body_detail(htmlBodyDetail);
			rssRepository.save(ni);
			return htmlBodyDetail;
		} else {
			return htmlBodyDetailFromDB;
		}
		
	}
	
	@RequestMapping(value = "/savePDF/{id}", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<byte[]> savePDF(@PathVariable Integer id) throws IOException {
		LOG.info("savePDF");
		String link = rssRepository.findById(id).get().getLink();
		System.out.println(link);
		
		/*ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
		String exePath = "D:\\chromedriver.exe";
    	System.setProperty("webdriver.chrome.driver", exePath);
    	WebDriver driver = new ChromeDriver(options);
    	driver.navigate().to("https://www.bbc.co.uk/newsround/50424586");
    	System.out.println("==> ");
    	System.out.println(driver.findElement(By.xpath("//section[@role='main']/section/div/div/div")).getAttribute("outerHTML"));*/
    	
		Document doc = Jsoup.connect(link).get();
		Elements element = doc.getElementsByClass("article-body");//getElementsByAttributeValue("class", "newsround-story-body__content").get(0).children();
		String body = element.text();
		HtmlConverter.convertToPdf(body, new FileOutputStream(id+".pdf"));
		
		FileInputStream fileStream;
        try {
            fileStream = new FileInputStream(new File(id+".pdf"));
            byte[] contents = IOUtils.toByteArray(fileStream);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
            System.out.println("==> " + response);
            return response;
        } catch (FileNotFoundException e) {
           System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
	}
	
	

}