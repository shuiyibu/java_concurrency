package schedule;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class TimerScheduler {
    /**
     * Scheduler solution
     * <p>
     * Timer/TimerTask
     * SchedulerExecutorService
     * crontab
     * cron4j
     * quartz
     * <p>
     * Timer: 当任务的时间超过了间隔，那么任务就不会按照设定周期执行
     * crontab：可以确保间隔的正确性
     *
     * @param args
     */
    public static void main(String[] args) {
        Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                out.println(System.currentTimeMillis());
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }
}
