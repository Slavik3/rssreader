package com.edvantis.rssreader.quartz;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.edvantis.rssreader.exception.SyntaxException;
import com.edvantis.rssreader.services.AddFeedsService;

@RestController
public class AddFeedsJob implements Job {

	@Autowired
	private AddFeedsService feedImporter;

	@Override
	public void execute(JobExecutionContext arg0) {
		try {
			feedImporter.addFeeds();
		} catch (SyntaxException e) {
			e.printStackTrace();
		}
		System.out.println();
	}

}
