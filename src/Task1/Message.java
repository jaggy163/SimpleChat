package Task1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Message {
    private String login;
    private String message;
    private String[] users;
    private Date time;

    public Message(String login, String message) {
        this.login=login;
        this.message=message;
        this.time= Calendar.getInstance().getTime();
    }

    public Message(String login, String message, String[] users) {
        this.login = login;
        this.message=message;
        this.users=users;
        this.time= Calendar.getInstance().getTime();
    }

    public String getLogin() {
        return login;
    }

    public String getMessage() {
        return message;
    }

    public String[] getUsers() {
        return users;
    }

    public Date getTime() {
        return time;
    }

    public void SetUsers(String users[]) {
        this.users=users;
    }

}
