package Task1;
// Задача - Клиент через сервер посылает сообщение другому клиенту


import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    private static UserList userList = new UserList();
    private static ServerSocket server;

    public static void main(String[] args) {

        try {
            server = new ServerSocket(8071);
            System.out.println("Initialized");
            while(true) {
                Socket socket = server.accept();
                System.out.println(socket.getInetAddress().getHostName() + " connected");
                ServerThread thread = new ServerThread(socket);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (server!=null) {
                    server.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static UserList getUserList() {
        return userList;
    }
}
