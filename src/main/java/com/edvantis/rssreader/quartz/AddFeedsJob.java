package com.edvantis.rssreader.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

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
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		System.out.println(formatter.format(date));
		
		try {
			feedImporter.addFeeds();
		} catch (SyntaxException e) {
			e.printStackTrace();
		}
		System.out.println();
	}

}
