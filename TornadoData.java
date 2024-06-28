import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class TornadoData {
    public static void main(String[] args) {
        String file = "us_tornado_dataset_1950_2021_mod.csv";
        String line = "";
        String separator = ",";
        Map<String, Integer> tornadoCount = new HashMap<>();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(separator);
                String dateStr = data[0];
                LocalDate date = LocalDate.parse(dateStr, dateFormatter);
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                String dayOfWeekStr = dayOfWeek.toString();
                tornadoCount.put(dayOfWeekStr, tornadoCount.getOrDefault(dayOfWeekStr, 0) + 1);
            }

            System.out.println("Day\tTornado Count");
            System.out.println("-----------------");
            tornadoCount.forEach((day, count) -> System.out.println(day + "\t" + count));

            int maxCount = tornadoCount.values().stream().mapToInt(Integer::intValue).max().orElse(0);
            System.out.println("\nDay(s) with the most tornadoes:");
            tornadoCount.forEach((day, count) -> {
                if (count == maxCount) {
                    System.out.println(day);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
