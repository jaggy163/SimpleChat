package Task1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InputThreadClient extends Thread {
    private ObjectInputStream ois;
    private Message message;
    public InputThreadClient(ObjectInputStream ois) {
        this.ois=ois;
    }

    @Override
    public void run() {
        try {

            while (true) {
                message = (Message) ois.readObject();
                String time = new SimpleDateFormat("HH:mm").format(message.getTime());
                System.out.println(time + " | " + message.getLogin() + ": " + message.getMessage());
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            System.err.println("Потеряно соединение с сервером чата.");
//            e.printStackTrace();
        }
    }
}
