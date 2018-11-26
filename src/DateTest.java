import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class DateTest {
    public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
    }
}
