import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TornadoData {
    public static void main(String[] args) {
        String file = "us_tornado_dataset_1950_2021_mod.csv";
        String line = "";
        String separator = ",";
        Map<String, Integer> tornadoCount = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(separator);
                String dateStr = data[0];
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateFormat.parse(dateStr));
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                String dayOfWeekStr = getDayOfWeek(dayOfWeek);
                tornadoCount.put(dayOfWeekStr, tornadoCount.getOrDefault(dayOfWeekStr, 0) + 1);
            }

            System.out.println("Day\tTornado Count");
            System.out.println("-----------------");
            for (Map.Entry<String, Integer> entry : tornadoCount.entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }

            int maxCount = 0;
            for (int count : tornadoCount.values()) {
                if (count > maxCount) {
                    maxCount = count;
                }
            }
            System.out.println("\nDay(s) with the most tornadoes:");
            for (Map.Entry<String, Integer> entry : tornadoCount.entrySet()) {
                if (entry.getValue() == maxCount) {
                    System.out.println(entry.getKey());
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static String getDayOfWeek(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "";
        }
    }
}