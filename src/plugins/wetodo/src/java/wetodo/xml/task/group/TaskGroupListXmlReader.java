package wetodo.xml.task.group;

import org.dom4j.Element;

public class TaskGroupListXmlReader {

    public static String getRoomid(Element lacoolElement) {
        String roomID = lacoolElement.attribute("roomid").getValue();

        return roomID;
    }
}
