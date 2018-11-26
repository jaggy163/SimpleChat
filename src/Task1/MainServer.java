package Task1;
// Задача - Клиент через сервер посылает сообщение другому клиенту


import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    private static UserList userList = new UserList();

    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(8070);
            System.out.println("Initialized");
            while(true) {
                Socket socket = server.accept();
                System.out.println(socket.getInetAddress().getHostName() + "connected");
                ServerThread thread = new ServerThread(socket);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserList getUserList() {
        return userList;
    }
}
