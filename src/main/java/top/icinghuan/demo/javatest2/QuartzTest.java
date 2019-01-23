package top.icinghuan.demo.javatest2;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author : xuyuan
 * @date : 2019-01-23
 * Description :
 */
public class QuartzTest {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 创建调度器
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        // 创建任务
        JobDetail jobDetail = JobBuilder.newJob(JobImpl.class).withIdentity("myJob", "jobGroup").build();

        // 创建触发器
        // 每隔1s执行任务
        Date triggerDate = new Date();
        SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever();
        TriggerBuilder<Trigger> triggerBuilder  = TriggerBuilder.newTrigger().withIdentity("myTrigger", "triggerGroup");
        Trigger trigger = triggerBuilder.startAt(triggerDate).withSchedule(schedBuilder).build();

        // 将任务及其触发器放入调度器
        scheduler.scheduleJob(jobDetail, trigger);

        // 调度器开始调度任务
        scheduler.start();
    }

    public static class JobImpl implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("job start");
            // TODO
            System.out.println("job end");
        }
    }
}
