package Task1;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class OutputThreadClient extends Thread {
    private ObjectOutputStream oos;
    private String login;
    Scanner sc = new Scanner(System.in);

    public OutputThreadClient(String login, ObjectOutputStream oos) {
        this.login=login;
        this.oos=oos;
    }

    @Override
    public void run() {
//        try {
//            Message first = new Message(login, "");
//            oos.writeObject(first);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            while (true) {
                String mes = sc.nextLine();
                Message message = new Message(login, mes);
                oos.writeObject(message);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
