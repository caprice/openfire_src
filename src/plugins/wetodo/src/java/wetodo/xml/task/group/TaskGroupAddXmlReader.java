package wetodo.xml.task.group;

import org.dom4j.Element;

public class TaskGroupAddXmlReader {

    public static void getTaskGroup(Element lacoolElement) {
        int roomID = Integer.parseInt(lacoolElement.attribute("roomid").getValue());

        Element itemElement = lacoolElement.element("item");
        String taskGroupName = itemElement.attribute("name").getValue();
    }
}
