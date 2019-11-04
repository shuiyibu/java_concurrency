package schedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static java.lang.System.out;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzSchedule {
    public static void main(String[] args) throws SchedulerException {
        JobDetail jobDetail = newJob(SimpleJob.class).withIdentity("Job1", "Group1").build();
        Trigger trigger = newTrigger().withIdentity("trigger1", "Group1")
                .withSchedule(cronSchedule("0/5 * * * * ?"))
                .build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
    }
}

/**
 * 为什么内部类不可以？
 */
class SimpleJob2 implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        out.println("=================" + System.currentTimeMillis());
//        try {
//            TimeUnit.MINUTES.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}