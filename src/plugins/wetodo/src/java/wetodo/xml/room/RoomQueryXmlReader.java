package wetodo.xml.room;

import org.dom4j.Element;

public class RoomQueryXmlReader {

    public static String getRoomId(Element lacoolElement) {
        return lacoolElement.attribute("roomid").getValue();
    }

}
