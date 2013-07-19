package wetodo.xml.task;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wetodo.model.Task;
import wetodo.model.TaskGroup;

import java.text.SimpleDateFormat;

public class TaskAddXmlWriter {
    public static Element write(Task task, TaskGroup taskGroup, String namespace) {
        Element lacoolElement = DocumentHelper.createElement("lacool");
        lacoolElement.addNamespace("", namespace);

        Element taskgroupElement = lacoolElement.addElement("taskgroup", namespace);
        taskgroupElement.addAttribute("tgid", String.valueOf(taskGroup.getTgid()));
        taskgroupElement.addAttribute("version", String.valueOf(taskGroup.getVersion()));

        Element taskElement = taskgroupElement.addElement("task");
        taskElement.addAttribute("tid", task.getTid());
        taskElement.addAttribute("name", task.getName());
        taskElement.addAttribute("status", String.valueOf(task.getStatus()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        taskElement.addAttribute("create_date", sdf.format(task.getCreate_date()));
        taskElement.addAttribute("modify_date", sdf.format(task.getModify_date()));
        return lacoolElement;
    }
}
