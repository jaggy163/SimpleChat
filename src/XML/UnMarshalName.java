package XML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class UnMarshalName {
    public static void main(String[] args) {
        try {
            JAXBContext context = JAXBContext.newInstance(Students.class);
            Unmarshaller u = context.createUnmarshaller();
            FileReader reader = new FileReader("data/studs_marsh.xml");
            Students students = (Students) u.unmarshal(reader);
            System.out.println(students);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
