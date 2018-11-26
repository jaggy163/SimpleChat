import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private final int DELAY = 30000;
    private ObjectOutputStream os = null; // Прием
    private ObjectInputStream is = null; // Передача
    private InetAddress address = null; // Адрес клиента
    private String con;
    private Socket socket;
    private Message message;
    private String log;
    private int inPacks;
    private int outPacks;
    private boolean flag = false;
    private Timer timer;

    public ServerThread(Socket socket) throws IOException {
        this.socket=socket;
        // Потоки ввода-вывода
        os = new ObjectOutputStream(this.socket.getOutputStream());
        is = new ObjectInputStream(this.socket.getInputStream());

    }

    @Override
    public void run() {
        try {
            message = (Message) is.readObject();
            this.log = message.getLog();

            // Если это не приветственное сообщение, то добавляем сообщение в историю чата.
            if (! message.getMessage().equals("User has been connected")) {
                System.out.println("[" + log + "]: " + message.getMessage());
                Server.getChatHistory().addMessage(message);
            } else {    // Если это приветственное сообщение, то надо показать ему историю и сообщаем остальным, что он подключился
                os.writeObject(Server.getChatHistory());
                this.broadcast(Server.getUserList().getClientsList(), new Message("Server-Bot", "The User " + log + " has been connected"));
            }

            //Добавляем к списку пользователей нового
            Server.getUserList().addUser(log, socket, os, is);

            // Для ответа, указываем список доступных пользователей
            message.setUsers(Server.getUserList().getUsers());

            //Передаем всем сообщение пользователя
            this.broadcast(Server.getUserList().getClientsList(), message);

            //Запускаем таймер
            this.timer = new Timer(DELAY, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try { //Если количество входящих пакетов от клиента равно исходящему, значит пингуется
                        if (inPacks == outPacks) {
                            os.writeObject(new Ping());
                            outPacks++;
                            System.out.println(outPacks + " out");
                        } else { // Если не пингуется
                            throw new SocketException();
                        }
                    } catch (SocketException ex1) {
                        System.out.println("packages not clash");
                        System.out.println(log + " disconnected!");
                        //Удаляем клиента из списка доступных и информируем всех
                        Server.getUserList().deleteUser(log);
                        broadcast(Server.getUserList().getClientsList(), new Message("Server-Bot", "The user " + log + " has been disconnect", Server.getUserList().getUsers()));
                        flag = true;
                        timer.stop();
                    }  catch (IOException ex1) {
                        ex1.printStackTrace();
                    }
                }
            });
            timer.start();

            //Начинаем пинговать клиента
            os.writeObject(new Ping());
            this.outPacks++;
            System.out.println(outPacks + " out");

            //А теперь нам остается только ждать от него сообщений
            while (true) {
                //Как только пинг пропал - заканчиваем
                if(this.flag) {
                    this.flag = false;
                    break;
                }
                //Принимаем сообщение
                this.message = (Message) is.readObject();

                //Если это ping
                if (this.message instanceof Ping) {
                    this.inPacks++;
                    System.out.println(this.inPacks + " in");
                } else if (! message.getMessage().equals("User has been connected")) {
                    System.out.println("[" + log + "]: " + message.getMessage());
                    Server.getChatHistory().addMessage(this.message);
                } else {
                    os.writeObject(Server.getChatHistory());
                    this.broadcast(Server.getUserList().getClientsList(), new Message("Server-Bot", "The user " + log + " has been connect"));
                }

                this.message.setUsers(Server.getUserList().getUsers());

                if (! (message instanceof Ping) && ! message.getMessage().equals("User has been connected")) {
                    System.out.println("Send broadcast Message: " + message.getMessage());
                    this.broadcast(Server.getUserList().getClientsList(), this.message);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcast(ArrayList<Client> clientsArrayList, Message message) {
        try {
            for (Client client : clientsArrayList) {
                client.getThisObjectOutputStream().writeObject(message);
            }
        } catch (SocketException e) {
            System.out.println("in broadcast: " + log + " disconnected!");
            Server.getUserList().deleteUser(log);
            this.broadcast(Server.getUserList().getClientsList(), new Message("System", "The user " + log + " has been disconnected", Server.getUserList().getUsers()));
            timer.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void disconnect() {
        try {
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }
}
