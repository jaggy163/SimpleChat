package Task1;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserList {
    private HashMap<String, Client> userList;

    public UserList() {
    }

    public void addUser(String login, Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
        if (!userList.containsKey(login)) {
            userList.put(login, new Client(socket, ois, oos));
        } else {
            int i = 2;
            String str = login;
            while (userList.containsKey(login)) {
                login = str + "(" + i + ")";
                i++;
            }
            userList.put(login, new Client(socket, ois, oos));
        }
    }

    public void deleteUser(String login) {
        userList.remove(login);
    }

    public HashMap<String, Client> getUsers() {
        return userList;
    }

    public String[] getUsersStringArray() {
        String[] str = userList.keySet().toArray(new String[0]);
        return str;
    }

    public ArrayList<Client> getUsersInArrayList() {
        ArrayList<Client> clientList = null;

        for (Map.Entry<String, Client> m : userList.entrySet()) {
            clientList.add(m.getValue());
            System.out.println(m.getKey());
        }

        return clientList;
    }
}
