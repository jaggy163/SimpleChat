import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static UserList list = new UserList();
    private static ChatHistory chatHistory = new ChatHistory();

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8085);
            System.out.println("Initialized");
            while (true) {
                Socket socket = server.accept();
                System.out.println(socket.getInetAddress().getHostName() + " connected.");
                ServerThread thread = new ServerThread(socket);
                thread.start();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static UserList getUserList() {
        return list;
    }

    public synchronized static ChatHistory getChatHistory() {
        return chatHistory;
    }
}
