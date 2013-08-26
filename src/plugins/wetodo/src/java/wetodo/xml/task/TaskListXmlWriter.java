package wetodo.xml.task;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.Task;
import wetodo.model.TaskGroup;

import java.util.List;

public class TaskListXmlWriter {
    public static Element write(String roomid, List<Task> list, TaskGroup taskGroup, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);
        lacoolElement.addAttribute("roomid", roomid);

        Element taskGroupElement = lacoolElement.addElement("taskgroup", namespace);
        taskGroupElement.addAttribute("tgid", taskGroup.getTgid());
        taskGroupElement.addAttribute("roomid", taskGroup.getRoomid());
        taskGroupElement.addAttribute("version", String.valueOf(taskGroup.getVersion()));

        taskGroupElement.addAttribute("create_date", String.valueOf(taskGroup.getCreate_date().getTime()));
        taskGroupElement.addAttribute("modify_date", String.valueOf(taskGroup.getModify_date().getTime()));

        for (Task task : list) {
            Element taskElement = taskGroupElement.addElement("task", namespace);
            taskElement.addAttribute("tid", task.getTid());
            taskElement.addAttribute("roomid", task.getRoomid());
            taskElement.addAttribute("name", task.getName());
            taskElement.addAttribute("status", String.valueOf(task.getStatus()));

            taskElement.addAttribute("create_date", String.valueOf(task.getCreate_date().getTime()));
            taskElement.addAttribute("modify_date", String.valueOf(task.getModify_date().getTime()));
        }

        return lacoolElement;
    }

}
