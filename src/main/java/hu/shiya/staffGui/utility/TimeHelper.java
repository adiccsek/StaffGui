package hu.shiya.staffGui.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeHelper {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String formatTimestamp(long millis) {
        return formatter.format(new Date(millis));
    }

    public static long parseFormattedDate(String formattedDate) {
        try {
            Date date = formatter.parse(formattedDate);
            return date.getTime();
        } catch (ParseException e) {
            System.err.println("Invalid date format. Use yyyy-MM-dd HH:mm");
            return -1;
        }
    }

    public static long parseTimeOffset(String input) {
        input = input.toLowerCase();
        long millis = 0;
        try {
            if (input.endsWith("d")) {
                int days = Integer.parseInt(input.replace("d", ""));
                millis = TimeUnit.DAYS.toMillis(days);
            } else if (input.endsWith("h")) {
                int hours = Integer.parseInt(input.replace("h", ""));
                millis = TimeUnit.HOURS.toMillis(hours);
            } else if (input.endsWith("m")) {
                int minutes = Integer.parseInt(input.replace("m", ""));
                millis = TimeUnit.MINUTES.toMillis(minutes);
            } else if (input.endsWith("month")) {
                int months = Integer.parseInt(input.replace("month", ""));
                millis = TimeUnit.DAYS.toMillis(30L * months); // becsült 30 nap/hónap
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid time format: " + input);
        }
        return millis;
    }

    public static boolean isExpired(long startTimestamp, long durationMillis) {
        return System.currentTimeMillis() >= (startTimestamp + durationMillis);
    }
}

