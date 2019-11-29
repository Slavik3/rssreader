package com.edvantis.rssreader.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigureAddFeedsJob {

	@Bean
	public JobDetail jobADetails() {
		return JobBuilder.newJob(AddFeedsJob.class).withIdentity("Add feeds job").storeDurably().build();
	}

	@Bean
	public Trigger jobATrigger(JobDetail jobADetails) {
		return TriggerBuilder.newTrigger().forJob(jobADetails).withIdentity("hour trigger")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 * ? * *")).build();
	}

}
