import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatHistory implements Serializable {
    private List<Message> history;

    public ChatHistory() {
        this.history = new ArrayList<Message>(20);
    }

    public void addMessage(Message message){
        if (this.history.size() > 20){
            this.history.remove(0);
        }

        this.history.add(message);
    }

    public List<Message> getHistory(){
        return this.history;
    }


}
