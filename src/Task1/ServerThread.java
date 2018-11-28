package Task1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
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
    private InetAddress addr;

    public ServerThread(Socket socket) {
        this.socket = socket;
        addr = socket.getInetAddress();
    }

    @Override
    public void run() {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            message = (Message) ois.readObject();
            login=getUserList().addUser(message.getLogin(), socket, ois, oos);
//            login=message.getLogin();
            oos.writeObject(new Message("Bot", login));
            System.out.println("Имя нового пользователя: " + login);
            Message welcome = new Message("Bot", login + " присоединился.");
            sendToAll(getUserList().getUsersInArrayList(), welcome);
//            getUserList().addUser(login, socket, ois, oos);

            message.SetUsers(getUserList().getUsersStringArray());

            while (true) {
                message = (Message) ois.readObject();
                sendToAll(getUserList().getUsersInArrayList(), message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        } finally {
            disconnect();
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

    public void disconnect() {
        try {
            if (oos!=null) oos.close();
            if (ois!=null) ois.close();
            System.out.println(login + " вышел из чата.");
            Message goodbye = new Message("Bot", login + " вышел из чата");
            sendToAll(getUserList().getUsersInArrayList(), goodbye);
            getUserList().deleteUser(login);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }

}
