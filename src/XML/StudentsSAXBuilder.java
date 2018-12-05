package XML;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.util.Set;

public class StudentsSAXBuilder {
    private Set<Student> students;
    private StudentHandler sh;
    private XMLReader reader;
    public StudentsSAXBuilder() {
        sh = new StudentHandler();
        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(sh);

        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public Set<Student> getStudents() {
        return students;
    }

    

}
