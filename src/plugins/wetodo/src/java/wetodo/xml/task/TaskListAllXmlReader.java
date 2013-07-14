package wetodo.xml.task;

import org.dom4j.Element;

public class TaskListAllXmlReader {

    public static int getRoomid(Element lacoolElement) {
        int roomid = Integer.parseInt(lacoolElement.attribute("roomid").getValue());
        return roomid;
    }

}
