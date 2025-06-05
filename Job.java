import java.time.*;

public class Job {
    private final String name;
    private final ScheduleType type;
    private final int minuteOfHour;
    private final int hourOfDay;
    private final DayOfWeek dayOfWeek;
    private LocalDateTime nextRun;

    public Job(String name, ScheduleType type, int minuteOfHour, int hourOfDay, DayOfWeek dayOfWeek) {
        this.name = name;
        this.type = type;
        this.minuteOfHour = minuteOfHour;
        this.hourOfDay = hourOfDay;
        this.dayOfWeek = dayOfWeek;
    }

    public void run() {
        System.out.println("🟢 Executing Job: " + name + " at " + LocalDateTime.now());
    }

    public void updateNextRun(LocalDateTime newTime) {
        this.nextRun = newTime;
    }

    public LocalDateTime getNextRun() {
        return nextRun;
    }

    public String getName() { return name; }
    public ScheduleType getType() { return type; }
    public int getMinuteOfHour() { return minuteOfHour; }
    public int getHourOfDay() { return hourOfDay; }
    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
}
