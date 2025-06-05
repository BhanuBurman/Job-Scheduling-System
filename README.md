# Java Job Scheduling System

This is a simple yet powerful **Job Scheduling System** built in Java. It lets you schedule jobs to run:
- **Hourly** (at a specific minute of the hour)
- **Daily** (at a specific time of the day)
- **Weekly** (on a specific day and time)

When the scheduled time arrives, the system **executes a simple task**, like printing "Hello World" (or any custom message) to the console.

---

## Features

-  Hourly, Daily, and Weekly scheduling support
-  Real-time countdown for upcoming jobs printed in the console
-  Repeating tasks at fixed intervals
-  Easy to extend (e.g., add logging, custom actions, file output)
-  Clean and readable code structure using modern Java best practices

---

##  Technologies Used

- Java 17+
- Java `ScheduledExecutorService`
- Java Time API (`LocalDateTime`, `Duration`, etc.)
- Multithreading for live monitoring

---

## How to Run

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/java-job-scheduler.git
cd java-job-scheduler
```
### 2. Compile and Run

```bash
javac *.java
java Main
```
Make sure you have JDK 17 or later installed.

### 3. Sample Output

```bash
Current Time: 2025-06-04T22:46:39
Job: Drinking....     | Next Run In: 00h 28m 20s | Scheduled at: 2025-06-04T23:15
Job: Going to School  | Next Run In: 14h 58m 20s | Scheduled at: 2025-06-05T13:45
Job: Playing Football | Next Run In: 35h 43m 20s | Scheduled at: 2025-06-06T10:30
```
