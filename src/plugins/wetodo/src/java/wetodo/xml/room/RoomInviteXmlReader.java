package wetodo.xml.room;

import org.dom4j.Element;
import org.xmpp.packet.JID;

public class RoomInviteXmlReader {

    public static String getRoomId(Element lacoolElement) {
        return lacoolElement.attribute("roomid").getValue();
    }

    public static JID getInviteeJid(Element lacoolElement) {
        Element inviteElement = lacoolElement.element("invite");
        return new JID(inviteElement.attribute("to").getValue());
    }
}
