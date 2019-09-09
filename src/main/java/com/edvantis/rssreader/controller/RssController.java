package com.edvantis.rssreader.controller;

import java.util.List;
import java.util.Optional;

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

import com.edvantis.rssreader.model.NewsItem;
import com.edvantis.rssreader.repository.RssRepository;

@RestController
@RequestMapping(value = "/feeds")
public class RssController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private RssRepository rssRepository;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public NewsItem addNewItem(@RequestBody NewsItem item) {
		LOG.info("Saving item.");
		return rssRepository.save(item);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<NewsItem> getAllItems(@RequestParam(value = "source", required = false) String source) {
		LOG.info("Getting all items.");
		if (source != null) {
			return rssRepository.findBySource(source);
		} else {
			return rssRepository.findAll();
		}
	}

	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	public Optional<NewsItem> getItem(@PathVariable String itemId) {
		LOG.info("Getting item with ID: {}.", itemId);
		return rssRepository.findById(itemId);
	}

	@RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteItem(@PathVariable String itemId) {
		rssRepository.deleteById(itemId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}