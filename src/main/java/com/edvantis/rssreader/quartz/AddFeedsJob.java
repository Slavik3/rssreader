package com.edvantis.rssreader.quartz;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddFeedsJob implements Job {
	
	@Autowired
	AddFeedsService addFeedsService;
	
	@Override
	public void execute(JobExecutionContext arg0) {
		addFeedsService.addFeeds();
	}
	
}
