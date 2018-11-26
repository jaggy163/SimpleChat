package Task1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) {
        Socket socket = null;
        ObjectInputStream ois;
        ObjectOutputStream oos;

        try {
            socket = new Socket(InetAddress.getLocalHost(), 8070);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите ваше имя и нажмите Enter:");
            String login = sc.nextLine();

            while (true) {
                String str = sc.nextLine();
                oos.writeObject(new Message(login, str));
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
