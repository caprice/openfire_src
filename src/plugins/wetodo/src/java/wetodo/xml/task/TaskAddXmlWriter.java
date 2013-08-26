package wetodo.xml.task;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.Task;
import wetodo.model.TaskGroup;

public class TaskAddXmlWriter {
    public static Element write(String roomid, Task task, TaskGroup taskGroup, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);
        lacoolElement.addAttribute("roomid", roomid);

        Element taskgroupElement = lacoolElement.addElement("taskgroup", namespace);
        taskgroupElement.addAttribute("tgid", String.valueOf(taskGroup.getTgid()));
        taskgroupElement.addAttribute("version", String.valueOf(taskGroup.getVersion()));

        Element taskElement = taskgroupElement.addElement("task");
        taskElement.addAttribute("tid", task.getTid());
        taskElement.addAttribute("name", task.getName());
        taskElement.addAttribute("status", String.valueOf(task.getStatus()));

        taskElement.addAttribute("create_date", String.valueOf(task.getCreate_date().getTime()));
        taskElement.addAttribute("modify_date", String.valueOf(task.getModify_date().getTime()));
        return lacoolElement;
    }
}
