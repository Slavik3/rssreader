package com.edvantis.rssreader.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edvantis.rssreader.model.Source;
import com.edvantis.rssreader.repository.SourceRepository;

@RestController
public class SourceController {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SourceRepository sourceRepository;
	
	@RequestMapping(value = "/addSource", method = RequestMethod.POST)
	public Source addSource(@RequestBody Source source) throws URISyntaxException {
		LOG.info("addSource");
		URI uri = null;
		uri = new URI(source.getSourceURL());
		String domain = uri.getHost();
		source.setHostname(domain);
		return sourceRepository.save(source);
	}
	
	@RequestMapping(value = "getSource", method = RequestMethod.GET)
	public List<Source> getAllSources(@RequestParam(value = "source", required = false) String source) {
		LOG.info("");
		return sourceRepository.findAll();
	}

}
