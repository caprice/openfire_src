package wetodo.xml.task;

import org.dom4j.Element;

public class TaskListXmlReader {

    public static String getRoomid(Element lacoolElement) {
        String roomid = lacoolElement.attribute("roomid").getValue();
        return roomid;
    }

    public static String getTgid(Element lacoolElement) {
        Element itemElement = lacoolElement.element("taskgroup");
        String tgid = itemElement.attribute("tgid").getValue();
        return tgid;
    }
}
