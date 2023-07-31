package controller.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class Utilities {
    private static final ConcurrentHashMap<String, Thread> threadMap = new ConcurrentHashMap<>();
    private static Thread backgroundThread;

    public static void main(String[] args) {
        ArrayList<String> threadIDs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String threadID = new Utilities().getRandom32String();
            threadIDs.add(threadID);
            backgroundThread = new Thread(() -> {
                long startTime = System.currentTimeMillis();
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupt occurred " + (System.currentTimeMillis() - startTime) / 1000 + "seconds later");
                }
            });
            threadMap.put(threadID, backgroundThread);
            backgroundThread.start();
            System.out.println("Background thread " + threadID + " started");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (String threadID : threadIDs) {
            System.out.println("Thread " + threadID + " is alive: " + threadMap.get(threadID).isAlive());
            Thread thread = threadMap.get(threadID);
            thread.interrupt();
            System.out.println("Thread " + threadID + "is Interrupted");
            try {
                thread.join();
                System.out.println("Thread " + threadID + " is joined");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public String getRandom32String() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    public long timeToMillis(int hours, int minutes, int seconds, int milliseconds) {
        long totalMilliseconds = 0;

        totalMilliseconds += (long) hours * 60 * 60 * 1000;       // Convert hours to milliseconds
        totalMilliseconds += (long) minutes * 60 * 1000;          // Convert minutes to milliseconds
        totalMilliseconds += seconds * 1000L;               // Convert seconds to milliseconds
        totalMilliseconds += milliseconds;                  // Add milliseconds

        return totalMilliseconds;
    }

    public long convertTimeStringToMillis(String timeString) {
        if (timeString.matches("\\d{2}:\\d{2}:\\d{2}\\.\\d{3}")) {
            timeString = timeString.substring(0, timeString.length() - 4);
        }
        if (timeString.matches("\\d{2}:\\d{2}")) {
            timeString += ":00";
        }
        String[] timeComponents = timeString.split(":");
        int hours = Integer.parseInt(timeComponents[0]);
        int minutes = Integer.parseInt(timeComponents[1]);
        int seconds = Integer.parseInt(timeComponents[2]);

        return timeToMillis(hours, minutes, seconds, 0);
    }

    public String convertDateToTimeString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return format.format(date);
    }

    public String convertLocalDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return localDateTime.format(formatter);
    }

    public String convertLocalTimeToString(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return localTime.format(formatter);
    }

    public String getCurrentTime() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return df.format(date);
    }
}
