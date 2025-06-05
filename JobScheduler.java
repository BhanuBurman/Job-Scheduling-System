import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JobScheduler {

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    public void schedule(Job job) {
        Runnable task = () -> {
            job.run();
            LocalDateTime next = calculateNextRun(job);
            job.updateNextRun(next);
        };

        LocalDateTime nextRun = calculateNextRun(job);
        job.updateNextRun(nextRun);
        long initialDelay = Duration.between(LocalDateTime.now(), nextRun).getSeconds();
        long period = switch (job.getType()) {
            case HOURLY -> 3600;
            case DAILY -> 86400;
            case WEEKLY -> 604800;
        };

        executorService.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }


    private long calculateDelayForHourly(int minuteOfHour) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.withSecond(0).withNano(0).plusHours(1).withMinute(minuteOfHour);
        if (now.getMinute() < minuteOfHour) {
            nextRun = now.withMinute(minuteOfHour).withSecond(0).withNano(0);
        }
        return Duration.between(now, nextRun).getSeconds();
    }

    private long calculateDelayForDaily(int hour, int minute) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.withHour(hour).withMinute(minute).withSecond(0).withNano(0);
        if (now.isAfter(nextRun)) {
            nextRun = nextRun.plusDays(1);
        }
        return Duration.between(now, nextRun).getSeconds();
    }

    private long calculateDelayForWeekly(DayOfWeek day, int hour, int minute) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.with(TemporalAdjusters.nextOrSame(day))
                .withHour(hour).withMinute(minute).withSecond(0).withNano(0);
        if (now.isAfter(nextRun)) {
            nextRun = nextRun.plusWeeks(1);
        }
        return Duration.between(now, nextRun).getSeconds();
    }

    private LocalDateTime calculateNextRun(Job job) {
        LocalDateTime now = LocalDateTime.now();
        return switch (job.getType()) {
            case HOURLY -> {
                LocalDateTime next = now.withSecond(0).withNano(0).plusHours(1).withMinute(job.getMinuteOfHour());
                if (now.getMinute() < job.getMinuteOfHour()) {
                    next = now.withMinute(job.getMinuteOfHour()).withSecond(0).withNano(0);
                }
                yield next;
            }
            case DAILY -> {
                LocalDateTime next = now.withHour(job.getHourOfDay()).withMinute(job.getMinuteOfHour()).withSecond(0).withNano(0);
                if (now.isAfter(next)) next = next.plusDays(1);
                yield next;
            }
            case WEEKLY -> {
                LocalDateTime next = now.with(TemporalAdjusters.nextOrSame(job.getDayOfWeek()))
                        .withHour(job.getHourOfDay()).withMinute(job.getMinuteOfHour()).withSecond(0).withNano(0);
                if (now.isAfter(next)) next = next.plusWeeks(1);
                yield next;
            }
        };
    }

}
