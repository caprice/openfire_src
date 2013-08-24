package wetodo.xml.room;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.Room;

import java.text.SimpleDateFormat;

public class RoomAddXmlWriter {

    public static Element write(Room room, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Element xElement = lacoolElement.addElement("x", "jabber:x:data");
        xElement.addAttribute("type", "result");

        Element fieldElement = xElement.addElement("field", "jabber:x:data");
        fieldElement.addAttribute("var", "FORM_TYPE");
        fieldElement.addAttribute("type", "hidden");

        Element valueElement = fieldElement.addElement("value", "jabber:x:data");
        valueElement.setText("http://jabber.org/protocol/muc#roominfo");

        fieldElement = xElement.addElement("field", "jabber:x:data");
        fieldElement.addAttribute("var", "muc#roominfo_subject");

        valueElement = fieldElement.addElement("value", "jabber:x:data");
        valueElement.setText(room.getSubject());

        fieldElement = xElement.addElement("field", "jabber:x:data");
        fieldElement.addAttribute("var", "muc#roominfo_description");

        valueElement = fieldElement.addElement("value", "jabber:x:data");
        valueElement.setText(room.getDescription());

        fieldElement = xElement.addElement("field", "jabber:x:data");
        fieldElement.addAttribute("var", "x-muc#roominfo_creationdate");

        valueElement = fieldElement.addElement("value", "jabber:x:data");
        valueElement.setText(sdf.format(room.getCreationdate()));


        System.out.println(lacoolElement);
        return lacoolElement;
    }

}

