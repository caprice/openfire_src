package wetodo.xml.task.group;

import org.dom4j.Element;
import wetodo.model.TaskGroup;

public class TaskGroupAddXmlReader {

    public static TaskGroup getTaskGroup(Element lacoolElement) {
        String roomid = lacoolElement.attribute("roomid").getValue();

        Element taskGroupElement = lacoolElement.element("taskgroup");
        String tgid = taskGroupElement.attribute("tgid").getValue();
        String taskGroupName = taskGroupElement.attribute("name").getValue();

        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setRoomid(roomid);
        taskGroup.setTgid(tgid);
        taskGroup.setName(taskGroupName);

        return taskGroup;
    }
}
