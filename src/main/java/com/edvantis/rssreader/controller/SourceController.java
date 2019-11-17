package com.edvantis.rssreader.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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

import com.edvantis.rssreader.model.Source;
import com.edvantis.rssreader.repository.SourceRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController

@Api(value = "sources", description = "Sourcs API")
public class SourceController {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SourceRepository sourceRepository;
	
	@ApiOperation(value = "Add new source")
	@RequestMapping(value = "/sources/add", method = RequestMethod.POST)
	public Source addSource(@RequestBody Source source) throws URISyntaxException {
		LOG.info("Add new source");
		URI uri = null;
		uri = new URI(source.getSourceURL());
		String domain = uri.getHost();
		source.setHostname(domain);
		return sourceRepository.save(source);
	}
	
	@RequestMapping(value = "/sources/getAll", method = RequestMethod.GET)
	@ApiOperation(value = "Retrieves all sources")
	public List<Source> getAllSources(@RequestParam(value = "source", required = false) String source) {
		LOG.info("Retrieves all sources");
		return sourceRepository.findAll();
	}
	
	@RequestMapping(value = "/sources/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable int id) {
		LOG.info("delete source");
		sourceRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/sources/{id}", method=RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody Source source) {
		LOG.info("update source");
		LOG.info(source.toString());
		sourceRepository.save(source);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
