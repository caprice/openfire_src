package wetodo.xml.task;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.Task;
import wetodo.model.TaskGroup;

import java.util.List;

public class TaskListXmlWriter {
    public static Element write(List<Task> list, TaskGroup taskGroup, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);

        Element taskGroupElement = lacoolElement.addElement("taskgroup");
        taskGroupElement.addAttribute("tgid", taskGroup.getTgid());
        taskGroupElement.addAttribute("roomid", String.valueOf(taskGroup.getRoomid()));
        taskGroupElement.addAttribute("version", String.valueOf(taskGroup.getVersion()));
        taskGroupElement.addAttribute("create_date", taskGroup.getCreate_date().toString());
        taskGroupElement.addAttribute("modify_date", taskGroup.getModify_date().toString());

        for (Task task : list) {
            Element taskElement = taskGroupElement.addElement("task");
            taskElement.addAttribute("tid", task.getTid());
            taskElement.addAttribute("roomid", String.valueOf(task.getRoomid()));
            taskElement.addAttribute("name", task.getName());
            taskElement.addAttribute("status", String.valueOf(task.getStatus()));
            taskElement.addAttribute("create_date", taskGroup.getCreate_date().toString());
            taskElement.addAttribute("modify_date", taskGroup.getModify_date().toString());
        }

        return lacoolElement;
    }

}
