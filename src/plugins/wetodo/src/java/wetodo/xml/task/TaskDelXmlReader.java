package wetodo.xml.task;

import org.dom4j.Element;
import wetodo.model.Task;

public class TaskDelXmlReader {

    public static Task getTask(Element lacoolElement) {
        String roomid = lacoolElement.attribute("roomid").getValue();

        Element taskGroupElement = lacoolElement.element("taskgroup");
        String tgid = taskGroupElement.attribute("tgid").getValue();

        Element taskElement = taskGroupElement.element("task");
        String tid = taskElement.attribute("tid").getValue();

        Task task = new Task();
        task.setTid(tid);
        task.setTgid(tgid);
        task.setRoomid(roomid);

        return task;
    }
}
