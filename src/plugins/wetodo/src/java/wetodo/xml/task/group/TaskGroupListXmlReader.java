package wetodo.xml.task.group;

import org.dom4j.Element;

public class TaskGroupListXmlReader {

    public static int getRoomid(Element lacoolElement) {
        int roomID = Integer.parseInt(lacoolElement.attribute("roomid").getValue());

        return roomID;
    }
}
