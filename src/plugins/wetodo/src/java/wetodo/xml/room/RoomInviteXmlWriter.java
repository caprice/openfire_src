package wetodo.xml.room;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.User;

public class RoomInviteXmlWriter {

    public static Element write(String namespace, User inviteeUser) {
        Element lacoolElement = DocumentHelper.createElement("lacool");

        Element friendElement = lacoolElement.addElement("friend", namespace);
        friendElement.addAttribute("jid", String.valueOf(inviteeUser.getJID()));
        friendElement.addAttribute("nickname", inviteeUser.getName());

        return lacoolElement;
    }
}
