import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Message implements Serializable {
    private String log;
    private String message;
    private String[] users;
    private Date time;

    // Конструктор для пользователя
    public Message(String log, String message) {
        this.log=log;
        this.message=message;
        this.time = Calendar.getInstance().getTime();
    }

    // Конструктор для сервера
    public Message(String log, String message, String[] users) {
        this.log = log;
        this.message=message;
        this.time = Calendar.getInstance().getTime();
        this.users = users;
    }

    public String getLog() {
        return log;
    }

    public String getMessage() {
        return message;
    }

    public String[] getUsers() {
        return users;
    }

    public Date getTime() {
        time = Calendar.getInstance().getTime();
        return time;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }
}
