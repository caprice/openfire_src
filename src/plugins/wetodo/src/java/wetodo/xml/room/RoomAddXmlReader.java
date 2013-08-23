package wetodo.xml.room;

import org.dom4j.Element;

public class RoomAddXmlReader {

    public static String getRoomId(Element lacoolElement) {
        String roomid = lacoolElement.attribute("roomid").getValue();
        return roomid;
    }
}
