package XML;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class Students {
    @XmlElement(name = "student")
    private ArrayList<Student> list = new ArrayList<>();
    public Students() {
    }

    public void setList(ArrayList<Student> list) {
        this.list = list;
    }

    public boolean add(Student st) {
        return list.add(st);
    }

    @Override
    public String toString() {
        return "Strudents [list=" + list + "]";
    }
}
