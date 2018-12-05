package XML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class StudentHandler extends DefaultHandler {
    private Set<Student> students;
    private Student current = null;
    private StudentEnum currentEnum = null;
    private EnumSet<StudentEnum> withText;

    public StudentHandler() {
        students = new HashSet<Student>();
        withText = EnumSet.range(StudentEnum.NAME, StudentEnum.STREET);
    }
    public Set<Student> getStudents() {
        return students;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("student".equals(localName)) {
            current = new Student();
            current.setLogin(attributes.getValue(0));
            if (attributes.getLength() == 2) {
                current.setFaculty(attributes.getValue(1));
            }
        } else {
            StudentEnum temp = StudentEnum.valueOf(localName.toUpperCase());
            if (withText.contains(temp)) {
                currentEnum = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("student".equals(localName)) {
            students.add(current);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s = new String(ch, start, length).trim();
        if (currentEnum != null) {
            switch (currentEnum) {
                case NAME:
                    current.setName(s);
                    break;
                case TELEPHONE:
                    current.setTelephone(s);
                    break;
                case STREET:
                    current.getAddress().setStreet(s);
                    break;
                case CITY:
                    current.getAddress().setCity(s);
                    break;
                case COUNTRY:
                    current.getAddress().setCountry(s);
                    break;
                    default:
                        throw new EnumConstantNotPresentException(currentEnum.getDeclaringClass(), currentEnum.name());
            }
        }
    }
}
