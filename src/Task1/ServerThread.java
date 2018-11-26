package Task1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import static Task1.MainServer.*;

public class ServerThread extends Thread {
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket socket;
    String login;
    Message message;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            message = (Message) ois.readObject();
            login=message.getLogin();

            getUserList().addUser(login, socket, ois, oos);

            message.SetUsers(getUserList().getUsersStringArray());

            while (true) {
                message = (Message) ois.readObject();
                sendToAll(getUserList().getUsersInArrayList(), message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendToAll(ArrayList<Client> arrayList, Message message) {
        try {
            for (Client client : arrayList) {
                client.getOos().writeObject(message);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
