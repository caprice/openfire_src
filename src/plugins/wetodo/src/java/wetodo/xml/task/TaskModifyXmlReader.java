package wetodo.xml.task;

import org.dom4j.Element;
import wetodo.model.Task;

public class TaskModifyXmlReader {

    public static Task getTask(Element lacoolElement) {
        String roomid = lacoolElement.attribute("roomid").getValue();

        Element taskGroupElement = lacoolElement.element("taskgroup");
        String tgid = taskGroupElement.attribute("tgid").getValue();

        Element taskElement = taskGroupElement.element("task");
        String tid = taskElement.attribute("tid").getValue();
        String name = taskElement.attribute("name").getValue();
        int status = Integer.parseInt(taskElement.attribute("status").getValue());

        Task task = new Task();
        task.setTid(tid);
        task.setTgid(tgid);
        task.setRoomid(roomid);
        task.setName(name);
        task.setStatus(status);

        return task;
    }
}
