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
		return JobBuilder.newJob(AddFeedsJob.class).withIdentity("sampleJobA").storeDurably().build();
	}

	@Bean
	public Trigger jobATrigger(JobDetail jobADetails) {

		return TriggerBuilder.newTrigger().forJob(jobADetails)

				.withIdentity("sampleTriggerA").withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * ? * * *"))
				.build();
	}

	@Bean
	public Trigger jobBTrigger(JobDetail jobBDetails) {

		return TriggerBuilder.newTrigger().forJob(jobBDetails)

				.withIdentity("sampleTriggerB").withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * ? * * *"))
				.build();
	}

}
