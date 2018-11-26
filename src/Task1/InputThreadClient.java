package Task1;

import java.io.ObjectInputStream;

public class InputThreadClient extends Thread {
    private ObjectInputStream ois;
    public InputThreadClient(ObjectInputStream ois) {
        this.ois=ois;
    }

    @Override
    public void run() {
        while (true) {
        }
    }
}
