package wetodo.xml.room;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.Room;

public class RoomQueryXmlWriter {

    public static Element write(Room room, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);

        Element roomElement = lacoolElement.addElement("room", namespace);
        roomElement.addAttribute("jid", String.valueOf(room.getRoomJid()));

        Element xElement = roomElement.addElement("x", "jabber:x:data");
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
        valueElement.setText(String.valueOf(room.getCreationdate().getTime()));

        fieldElement = xElement.addElement("field", "jabber:x:data");
        fieldElement.addAttribute("var", "muc#roominfo_ownerjid");

        valueElement = fieldElement.addElement("value", "jabber:x:data");
        valueElement.setText(String.valueOf(room.getOwner().getJID()));

        return lacoolElement;
    }

}
