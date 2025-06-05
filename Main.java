import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JobScheduler scheduler = new JobScheduler();

        // This jobs runs hourly
        Job hourlyJob = new Job("Drinking....", ScheduleType.HOURLY, 15, 0, null);
        // This jobs runs daily
        Job dailyJob = new Job("Going to School", ScheduleType.DAILY, 45, 13, null);
        // This jobs runs weekly on specific day
        Job weeklyJob = new Job("Playing Football", ScheduleType.WEEKLY, 30, 10, java.time.DayOfWeek.FRIDAY);

        List<Job> jobs = List.of(hourlyJob, dailyJob, weeklyJob);
        for (Job job : jobs) {
            scheduler.schedule(job);
        }

        // Start a clock display in another thread
        new Thread(() -> {
            while (true) {
                System.out.println("\n Current Time: " + LocalDateTime.now());
                for (Job job : jobs) {
                    Duration remaining = Duration.between(LocalDateTime.now(), job.getNextRun());
                    if (!remaining.isNegative()) {
                        long h = remaining.toHours();
                        long m = remaining.toMinutesPart();
                        long s = remaining.toSecondsPart();
                        System.out.printf(" Job: %-12s | Next Run In: %02dh %02dm %02ds | Scheduled at: %s%n",
                                job.getName(), h, m, s, job.getNextRun());
                    }
                }
                try {
                    Thread.sleep(5000); // Refresh every 5 seconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}
