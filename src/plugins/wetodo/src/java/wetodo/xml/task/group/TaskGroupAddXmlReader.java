package wetodo.xml.task.group;

import org.dom4j.Element;
import wetodo.model.TaskGroup;

public class TaskGroupAddXmlReader {

    public static TaskGroup getTaskGroup(Element lacoolElement) {
        int roomid = Integer.parseInt(lacoolElement.attribute("roomid").getValue());

        Element itemElement = lacoolElement.element("item");
        String taskGroupName = itemElement.attribute("name").getValue();

        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setRoomid(roomid);
        taskGroup.setName(taskGroupName);

        return taskGroup;
    }
}
