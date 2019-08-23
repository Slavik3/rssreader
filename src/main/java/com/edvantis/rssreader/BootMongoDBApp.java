package com.edvantis.rssreader;

import java.util.Timer;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.edvantis.rssreader.controller.QuartzJob;
import com.edvantis.rssreader.controller.RssController;
import com.edvantis.rssreader.repository.RssRepository;
import com.edvantis.rssreader.services.FeedImporter;

@SpringBootApplication
public class BootMongoDBApp {

	
	public static void main(String[] args) {
		/*Scheduler scheduler;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			JobDetail job = newJob()
		} catch (SchedulerException e) {
			e.printStackTrace();
		}*/
		
		try {
			 
			// email job details..
			JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class).withIdentity("emailJob").build();
 
			// specify job trigger
			Trigger trigger = TriggerBuilder.newTrigger()
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever())
					.build();
			// Run every 5 seconds.
 
			// Schedule the job
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();
			scheduler.start();
			scheduler.scheduleJob(jobDetail, trigger);
 
		} catch (SchedulerException e) {
			System.out.println("SchedulerException" + e.getMessage());
		}
		
		
		
		SpringApplication.run(BootMongoDBApp.class, args);
	}
	
	

	
}