import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static String formatDate(LocalDate date) {
        return date.format(DEFAULT_FORMATTER);
    }
    
    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DEFAULT_FORMATTER);
    }
    
    public static long daysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }
    
    public static boolean isValidRentalPeriod(LocalDate rentalDate, LocalDate returnDate) {
        return returnDate.isAfter(rentalDate);
    }
}
