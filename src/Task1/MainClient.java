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
        String login;

        try {
            socket = new Socket(InetAddress.getLocalHost(), 8071);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите ваше имя и нажмите Enter:");
            login = sc.nextLine();

            try {
                // Запрос свободного имени
                Message first = new Message(login, "");
                oos.writeObject(first);
                // Ответ присваемого имени
                login = ((Message)ois.readObject()).getMessage();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            InputThreadClient in = new InputThreadClient(ois);
            OutputThreadClient out = new OutputThreadClient(login, oos);
            in.start();
            out.start();


        }catch (IOException e) {
            System.err.println("Нет соединения с сервером чата.");
            e.printStackTrace();
        }
    }
}
