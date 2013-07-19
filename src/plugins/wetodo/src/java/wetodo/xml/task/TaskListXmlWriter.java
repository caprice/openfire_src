package wetodo.xml.task;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.Task;
import wetodo.model.TaskGroup;

import java.text.SimpleDateFormat;
import java.util.List;

public class TaskListXmlWriter {
    public static Element write(List<Task> list, TaskGroup taskGroup, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Element taskGroupElement = lacoolElement.addElement("taskgroup", namespace);
        taskGroupElement.addAttribute("tgid", taskGroup.getTgid());
        taskGroupElement.addAttribute("roomid", String.valueOf(taskGroup.getRoomid()));
        taskGroupElement.addAttribute("version", String.valueOf(taskGroup.getVersion()));

        taskGroupElement.addAttribute("create_date", sdf.format(taskGroup.getCreate_date()));
        taskGroupElement.addAttribute("modify_date", sdf.format(taskGroup.getModify_date()));

        for (Task task : list) {
            Element taskElement = taskGroupElement.addElement("task", namespace);
            taskElement.addAttribute("tid", task.getTid());
            taskElement.addAttribute("roomid", String.valueOf(task.getRoomid()));
            taskElement.addAttribute("name", task.getName());
            taskElement.addAttribute("status", String.valueOf(task.getStatus()));

            taskElement.addAttribute("create_date", sdf.format(task.getCreate_date()));
            taskElement.addAttribute("modify_date", sdf.format(task.getModify_date()));
        }

        return lacoolElement;
    }

}
